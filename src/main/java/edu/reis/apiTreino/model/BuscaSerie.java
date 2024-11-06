package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;


public record BuscaSerie(@SerializedName("Title") String titulo,
                         @SerializedName("Genre") String genero,
                         @SerializedName("totalSeasons") String temporadas,
                         @SerializedName("imdbRating") String nota,
                         @SerializedName("Year") String ano,
                         @SerializedName("Plot") String sinopse,
                         @SerializedName("Language") String idiomas,
                         @SerializedName("Country") String pais,
                         @SerializedName("Type") String tipo,
                         @SerializedName("Poster") String poster) {

    @Override
    public String toString() {
        return """
                
                ======== Resultado Para %s ========
                
                Serie: %s
                Gênero: %s
                Total de temporadas: %s
                Nota: %s
                Ano: %s
                Sinopse: %s
                Pais: %s
                Tipo: %s
                Poster: %s
                
                """.formatted(titulo, titulo, genero, temporadas, nota, ano, sinopse, pais, tipo, poster);
    }
}
