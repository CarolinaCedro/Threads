package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class medirTempoResposta {


    public static void main(String[] args) {


        List<String> urls = new ArrayList<>();
        urls.add("https://www.google.com");
        urls.add("https://www.github.com");
        urls.add("https://www.python.org");
        urls.add("https://www.wikipedia.org");


        List<Thread> threads = new ArrayList<>();

        // Criar uma thread para cada URL
        for (String url : urls) {
            Thread thread = new Thread(() -> medirTempoResposta(url));
            threads.add(thread);
            thread.start();
        }

        // Esperar todas as threads terminarem
        for (Thread thread : threads) {
            try {
                thread.join(); // Espera a thread atual terminar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Todas as requisições foram concluídas.");
    }


    public static void medirTempoResposta(String urlString) {
        try {
            long inicio = System.currentTimeMillis(); // Tempo inicial
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                long fim = System.currentTimeMillis(); // Tempo final
                long tempoResposta = fim - inicio;
                System.out.println("Site: " + urlString + " | Tempo de resposta: " + tempoResposta + " ms");
            } else {
                System.out.println("Falha ao acessar " + urlString + " | Código de resposta: " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar " + urlString + ": " + e.getMessage());
        }
    }


}
