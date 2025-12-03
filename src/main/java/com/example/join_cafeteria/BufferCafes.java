package com.example.join_cafeteria;

import java.util.LinkedList;
import java.util.Queue;

public class BufferCafes {
    private Queue<Cliente> buffer = new LinkedList<>();
    private int capacidad;

    public BufferCafes(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void ponerCafe(Cliente cliente) throws InterruptedException {
        while (buffer.size() >= capacidad) {
            wait();
        }
        buffer.add(cliente);
        notifyAll();
    }

    public synchronized Cliente tomarCafe() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        Cliente cliente = buffer.poll();
        notifyAll();
        return cliente;
    }
}
