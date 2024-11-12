package edu.reis.apiTreino.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsumoAPIChatGPT {




    public String traduzir(String texto) {

        OpenAiService service = new OpenAiService("sk-proj-vEwInzRdpZCruRV1nT9AkB87Az9R0XvNX6AuEXyePEHIoxFCpvCIs" +
                "TQPA5lt3bvKorvkkMQiajT3BlbkFJN9Bvzqj7qugqSZNLhl1cjt699Gaq3XabLkkvo59J0F3ZwZ41S4ZZQ9A1vtoF00BON9lzMAd9YA");

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();

    }
}
