package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public record RespostaGemini(@SerializedName("candidates") List<Candidato> candidatos) {

    public record Candidato(@SerializedName("content") Contem contem) {
    }

    public record Contem(@SerializedName("parts") List<Parte> partes) {
    }

    public record Parte(@SerializedName("text") String texto) {
    }

    public String getTexto() {
        String texto = candidatos.stream()
                .map(Candidato::contem)
                .flatMap(contem -> contem.partes().stream())
                .map(Parte::texto)
                .findFirst()
                .orElse("Sem Tradução::stream");

        return texto;
    }
}
