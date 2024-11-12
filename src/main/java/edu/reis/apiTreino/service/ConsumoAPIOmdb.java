package edu.reis.apiTreino.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPIOmdb {

    public String obterConsumo(String linkApi) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(linkApi))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException | NullPointerException e) {
            System.out.println("Algo de errado não está certo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Usuário...: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Volta, Deu Cagada!!!: " + e.getMessage());
        }
        return "Erro ao obter consumo";
    }
}

