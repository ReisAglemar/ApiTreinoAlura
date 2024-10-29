package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record ListaEpisodio(@SerializedName("Season") String temporada,
                            @SerializedName("Episodes") List<ModeloEpisodio> episodios) {
}
