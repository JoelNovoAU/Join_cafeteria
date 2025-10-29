package com.example.join_cafeteria;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

public class ControladorCafeteria {

    @FXML
    private Button botonIniciar;

    @FXML
    private Label labelContentos;

    @FXML
    private Label labelEnfadados;

    @FXML
    private VBox vboxEventos;

    @FXML
    private ScrollPane scrollEventos;

    private Cafeteria cafeteria;

    private int clientesContentos = 0;
    private int clientesEnfadados = 0;

    public enum TipoEvento {
        CLIENTE_CONTENTO,
        CLIENTE_ENFADADO,
        CAMARERO,
        INFO
    }

    @FXML
    private void initialize() {
        cafeteria = new Cafeteria(this);
    }

    @FXML
    private void iniciarSimulacion() {
        botonIniciar.setDisable(true);
        agregarEvento("Bienvenido a Cafetería NoVoToAsT", TipoEvento.INFO);

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
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }

            try { Thread.sleep(15000); } catch (InterruptedException e) {}

            cafeteria.cerrar();
            agregarEvento("La cafetería ha cerrado", TipoEvento.INFO);

            Platform.runLater(() -> botonIniciar.setDisable(false));
        }).start();
    }

    public void agregarEvento(String texto, TipoEvento tipo) {
        Platform.runLater(() -> {
            Label label = new Label(texto);
            label.setWrapText(true);
            label.setMaxWidth(vboxEventos.getWidth() - 20);
            label.setStyle(getEstiloEvento(tipo));
            vboxEventos.getChildren().add(label);

            scrollEventos.layout();
            scrollEventos.setVvalue(1.0); // Auto-scroll
        });
    }

    private String getEstiloEvento(TipoEvento tipo) {
        switch (tipo) {
            case CLIENTE_CONTENTO:
                return "-fx-text-fill: #3C763D; -fx-font-weight: bold;";
            case CLIENTE_ENFADADO:
                return "-fx-text-fill: #A94442; -fx-font-weight: bold;";
            case CAMARERO:
                return "-fx-text-fill: #6B4226;";
            case INFO:
            default:
                return "-fx-text-fill: #000000;";
        }
    }

    public synchronized void clienteContento() {
        clientesContentos++;
        Platform.runLater(() -> labelContentos.setText(String.valueOf(clientesContentos)));
    }

    public synchronized void clienteEnfadado() {
        clientesEnfadados++;
        Platform.runLater(() -> labelEnfadados.setText(String.valueOf(clientesEnfadados)));
    }
}
