package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

public record EpisodioEspecifico(@SerializedName("Title") String titulo,
                                 @SerializedName("Plot") String descricao,
                                 @SerializedName("Season") String temporada,
                                 @SerializedName("Episode") String episodio,
                                 @SerializedName("Runtime") String duracao,
                                 @SerializedName("imdbRating") String nota) {

    @Override
    public String toString() {
        return """
                
                ======== Resultado Para Episodio %s ========
                
                Nome Episodio: %s
                Descrição: %s
                Duração: %s
                Temporada: %s
                Episodio nº: %s
                Nota: %s
                
                """.formatted(episodio, titulo, descricao, duracao, temporada, episodio, nota);
    }
}

