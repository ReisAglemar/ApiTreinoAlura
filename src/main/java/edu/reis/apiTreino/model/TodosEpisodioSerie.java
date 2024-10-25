package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record TodosEpisodioSerie(@SerializedName("Season") String temporada,
                                 @SerializedName("Episodes") List<EpisodiosTemporadaEspecifica> episodios) {
}
