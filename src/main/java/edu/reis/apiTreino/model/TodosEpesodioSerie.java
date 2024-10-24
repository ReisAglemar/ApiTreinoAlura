package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record TodosEpesodioSerie(@SerializedName("Season") String temporada,
                                 @SerializedName("Episodes") List<EpisodioEspecifico> episodios) {
}
