package com.example.join_cafeteria;

import java.util.LinkedList;
import java.util.Queue;

public class Cafeteria {
    private Queue<Cliente> colaClientes = new LinkedList<>();
    private boolean abierta = true;

    private int clientesContentos = 0;
    private int clientesEnfadados = 0;

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
            try { wait(); } catch (InterruptedException e) { return null; }
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
        System.out.println(mensaje);
    }

    public synchronized void clienteSeFue(Cliente cliente, boolean atendido) {
        if (atendido) {
            clientesContentos++;
            mostrarEvento(cliente.getNombre() + " ha sido atendido. Clientes contentos: " + clientesContentos);
        } else {
            clientesEnfadados++;
            mostrarEvento(cliente.getNombre() + " se ha ido (esperó demasiado tiempo). Clientes enfadados: " + clientesEnfadados);
        }
    }
}
