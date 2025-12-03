package com.example.join_cafeteria;

public class Camarero extends Thread {
    private String nombre;
    private Cafeteria cafeteria;
    private BufferCafes buffer;

    public Camarero(String nombre, Cafeteria cafeteria, BufferCafes buffer) {
        this.nombre = nombre;
        this.cafeteria = cafeteria;
        this.buffer = buffer;
    }

    private void servirCafe(Cliente cliente) {
        cafeteria.mostrarEvento(nombre + " está sirviendo el café a " + cliente.getNombre(),
                ControladorCafeteria.TipoEvento.CAMARERO);
        cafeteria.clienteSeFue(cliente, true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Cliente cliente = buffer.tomarCafe();
                if (cliente == null) break;
                servirCafe(cliente);
            } catch (InterruptedException e) {
                break;
            }
        }
        cafeteria.mostrarEvento(nombre + " ha terminado su turno", ControladorCafeteria.TipoEvento.CAMARERO);
    }
}
