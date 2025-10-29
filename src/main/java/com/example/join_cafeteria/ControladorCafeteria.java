package com.example.join_cafeteria;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ControladorCafeteria {

    @FXML
    private TextArea areaEventos;

    @FXML
    private Button botonIniciar;

    private Cafeteria cafeteria;

    @FXML
    private void initialize() {
        cafeteria = new Cafeteria(this);
    }

    @FXML
    private void iniciarSimulacion() {
        botonIniciar.setDisable(true);
        agregarEvento("Bienvenido Cafetetira NoVoToAsT");

        new Thread(() -> {
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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {}

            cafeteria.cerrar();
            agregarEvento("La cafetería ha cerrado ");

            Platform.runLater(() -> botonIniciar.setDisable(false));
        }).start();
    }

    public void agregarEvento(String texto) {
        Platform.runLater(() -> areaEventos.appendText(texto + "\n"));
    }
}
