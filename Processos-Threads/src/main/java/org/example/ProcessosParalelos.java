package org.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProcessosParalelos {

    // Fila para armazenar os números gerados
    private static final Queue<Integer> fila = new LinkedList<>();
    private static final int LIMITE = 10;

    public static void main(String[] args) {

        // Thread 1: Esse cara produz
        Thread produtor = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < LIMITE; i++) {
                int num = random.nextInt(100);
                //Esse synchronized basicamente tá isolando o processo em que a thread está para que não haja nenhum processo posterior até essa thread ser encerrada para aí
                //sim começar uma outra. de forma resumida meio que ele isola o escopo do processo e só libera após terminar. evitar erros e tals
                synchronized (fila) {
                    fila.add(num);
                    System.out.println("Número gerado: " + num);
                    fila.notify(); // Notifica a outra thread que há um novo número na fila
                }
                try {
                    System.out.println("Processando ...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Esse cara consome
        Thread consumidor = new Thread(() -> {
            int soma = 0;
            for (int i = 0; i < LIMITE; i++) {
                synchronized (fila) {
                    while (fila.isEmpty()) {
                        try {
                            fila.wait(); // Aguarda até que haja um número na fila
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int num = fila.poll(); // Remove o número da fila
                    soma += num;
                    System.out.println("Número consumido: " + num);
                }
            }
            System.out.println("Soma total: " + soma);
        });

        // Iniciar os threads
        produtor.start();
        consumidor.start();

        // Espera até que ambos terminem
        try {
            produtor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
