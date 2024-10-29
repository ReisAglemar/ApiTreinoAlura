package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

public record ModeloEpisodio(@SerializedName("Title") String titulo,
                             @SerializedName("Episode") String episodio,
                             @SerializedName("imdbRating") String nota,
                             @SerializedName("Released") String data) {


    @Override
    public String toString() {
        return """
                
                ======== Resultado Para Episodio %s ========
                
                Nome Episodio: %s
                Episodio nº: %s
                Nota: %s
                
                """.formatted(episodio, titulo, episodio, nota);
    }
}

