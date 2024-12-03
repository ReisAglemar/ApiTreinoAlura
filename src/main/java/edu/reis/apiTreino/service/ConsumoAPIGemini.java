package edu.reis.apiTreino.service;

import edu.reis.apiTreino.model.RespostaGemini;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPIGemini {

    private final ConverteJsonClasse CONVERSOR = new ConverteJsonClasse();
    private final String apiKey = System.getenv("KEY_GEMINI");


    public String obterConsumo(String texto) {

        String link = "https://generativelanguage.googleapis.com/v1beta/models/" +
                "gemini-1.5-flash:generateContent?key=" + apiKey;

        String corpoJson = """
                {
                        "contents": [{
                            "parts": [{
                                "text": "Traduza o para o Português PT-BR o seguinte texto: %s"
                            }]
                        }]
                    }
                """.formatted(texto);

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(link))
                    .POST(HttpRequest.BodyPublishers.ofString(corpoJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                RespostaGemini respostaGemini = CONVERSOR.converteTipos(response.body(), RespostaGemini.class);
                return respostaGemini.getTexto();

            } else {
                return """
                        Erro ao Solicitar a Tradução!
                        
                        Código de Resposta do Serviço: %d
                        """.formatted(response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Volta, Deu Cagada!!!: " + e.getMessage());
        }
        return "Erro Lógico na Função! Caraca 02, Acorda Mano!";
    }
}