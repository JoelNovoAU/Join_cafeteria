package com.example.join_cafeteria;

public class Barista extends Thread {
    private String nombre;
    private Cafeteria cafeteria;
    private BufferCafes buffer;

    public Barista(String nombre, Cafeteria cafeteria, BufferCafes buffer) {
        this.nombre = nombre;
        this.cafeteria = cafeteria;
        this.buffer = buffer;
    }

    private void prepararCafe(Cliente cliente) throws InterruptedException {
        int tiempoPreparacion = (int)(Math.random() * 3000 + 2000);
        cafeteria.mostrarEvento(nombre + " está preparando el café de " + cliente.getNombre(),
                ControladorCafeteria.TipoEvento.BARISTA);
        Thread.sleep(tiempoPreparacion);
        cafeteria.mostrarEvento(nombre + " terminó el café de " + cliente.getNombre() +
                " (" + tiempoPreparacion + " ms)", ControladorCafeteria.TipoEvento.BARISTA);
    }

    @Override
    public void run() {
        while (true) {
            Cliente cliente = cafeteria.obtenerSiguienteCliente();
            if (cliente == null) break;
            try {
                prepararCafe(cliente);
                buffer.ponerCafe(cliente);
            } catch (InterruptedException e) {
                break;
            }
        }
        cafeteria.mostrarEvento(nombre + " ha terminado su turno", ControladorCafeteria.TipoEvento.BARISTA);
    }
}
