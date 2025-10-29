package com.example.join_cafeteria;

public class Camarero extends Thread {
    private String nombre;
    private Cafeteria cafeteria;

    public Camarero(String nombre, Cafeteria cafeteria) {
        this.nombre = nombre;
        this.cafeteria = cafeteria;
    }

    private void prepararCafe(Cliente cliente) {
        try {
            int tiempoPreparacion = (int)(Math.random() * 3000 + 2000);
            cafeteria.mostrarEvento(nombre + " está preparando el café de " + cliente.getNombre(),
                    ControladorCafeteria.TipoEvento.CAMARERO);
            Thread.sleep(tiempoPreparacion);
            cafeteria.mostrarEvento(nombre + " ha entregado el café a " + cliente.getNombre() +
                    " (" + tiempoPreparacion + " ms)", ControladorCafeteria.TipoEvento.CAMARERO);
        } catch (InterruptedException e) {
            cafeteria.mostrarEvento(nombre + " fue interrumpido mientras preparaba el café",
                    ControladorCafeteria.TipoEvento.CAMARERO);
        }
    }

    @Override
    public void run() {
        while (true) {
            Cliente cliente = cafeteria.obtenerSiguienteCliente();
            if (cliente == null) break;
            prepararCafe(cliente);
        }
        cafeteria.mostrarEvento(nombre + " ha terminado su turno", ControladorCafeteria.TipoEvento.CAMARERO);
    }
}
