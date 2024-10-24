package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;


public record Serie(@SerializedName("Title") String titulo,
                    @SerializedName("totalSeasons") String temporadas,
                    @SerializedName("imdbRating") String nota) {

    @Override
    public String toString() {
        return """
                
                ======== Resultado Para %s ========
                
                Serie: %s
                Total de temporadas: %s
                Nota: %s
                
                """.formatted(titulo, titulo, temporadas, nota);
    }
}
