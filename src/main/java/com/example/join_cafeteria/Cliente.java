package com.example.join_cafeteria;

public class Cliente implements Runnable {
    private String nombre;
    private int tiempoEspera;
    private Cafeteria cafeteria;

    public Cliente(String nombre, int tiempoEspera, Cafeteria cafeteria) {
        this.nombre = nombre;
        this.tiempoEspera = tiempoEspera;
        this.cafeteria = cafeteria;
    }

    public String getNombre() { return nombre; }
    public int getTiempoEspera() { return tiempoEspera; }

    @Override
    public void run() {
        cafeteria.mostrarEvento(nombre + " entra a la cafetería y pide un café", ControladorCafeteria.TipoEvento.INFO);
        boolean atendido = cafeteria.solicitarCafe(this);
        if (!atendido) {
            cafeteria.clienteSeFue(this, false);
        } else {
            cafeteria.clienteSeFue(this, true);
        }
    }
}
