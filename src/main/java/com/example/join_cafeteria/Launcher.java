package com.example.join_cafeteria;

public class Launcher {
    public static void main(String[] args) {
        Cafeteria cafeteria = new Cafeteria();

        Camarero c1 = new Camarero("Camarero Juan", cafeteria);
        Camarero c2 = new Camarero("Camarera Ana", cafeteria);

        c1.start();
        c2.start();

        Cliente[] clientes = {
                new Cliente("Carlos", 5000, cafeteria),
                new Cliente("María", 3000, cafeteria),
                new Cliente("Luis", 4000, cafeteria),
                new Cliente("Elena", 6000, cafeteria),
                new Cliente("Pedro", 2000, cafeteria)
        };

        for (Cliente cliente : clientes) {
            new Thread(cliente).start();
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }

        try { Thread.sleep(15000); } catch (InterruptedException e) {}

        cafeteria.cerrar();
        cafeteria.mostrarEvento("La cafetería ha cerrado");
    }
}
