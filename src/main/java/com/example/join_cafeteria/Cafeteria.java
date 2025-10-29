package com.example.join_cafeteria;

import java.util.LinkedList;
import java.util.Queue;

public class Cafeteria {
    private Queue<Cliente> colaClientes = new LinkedList<>();
    private boolean abierta = true;
    private ControladorCafeteria controlador;

    public Cafeteria(ControladorCafeteria controlador) {
        this.controlador = controlador;
    }

    public synchronized boolean solicitarCafe(Cliente cliente) {
        try {
            colaClientes.add(cliente);
            notifyAll();
            wait(cliente.getTiempoEspera());
            if (colaClientes.contains(cliente)) {
                colaClientes.remove(cliente);
                return false;
            }
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public synchronized Cliente obtenerSiguienteCliente() {
        while (colaClientes.isEmpty() && abierta) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        if (!abierta && colaClientes.isEmpty()) return null;
        Cliente cliente = colaClientes.poll();
        notifyAll();
        return cliente;
    }

    public synchronized void cerrar() {
        abierta = false;
        notifyAll();
    }

    public void mostrarEvento(String mensaje) {
        if (controlador != null) controlador.agregarEvento(mensaje);
        System.out.println(mensaje);
    }
}

