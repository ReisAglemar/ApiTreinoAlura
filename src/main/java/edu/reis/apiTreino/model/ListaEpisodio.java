package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record ListaEpisodio(@SerializedName("Season") String temporada,
                            @SerializedName("Title") String nomeSerie,
                            @SerializedName("Episodes") List<ModeloEpisodio> episodios) {
}
