package edu.reis.apiTreino.service;

import edu.reis.apiTreino.dto.ModeloEpisodioPessoalDto;
import edu.reis.apiTreino.dto.ModeloSeriePessoalDto;
import edu.reis.apiTreino.model.ModeloEpisodioPessoal;
import edu.reis.apiTreino.model.ModeloSeriePessoal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverteParaDto {

    public ConverteParaDto() {

    }

    public List<ModeloSeriePessoalDto> coverteSerieParaDto(List<ModeloSeriePessoal> modeloSeriePessoal) {
        return modeloSeriePessoal.stream()
                .map(s -> new ModeloSeriePessoalDto(s.getId(),
                        s.getTitulo(),
                        s.getGenero(),
                        s.getTemporadas(),
                        s.getNota(),
                        s.getAno(),
                        s.getIdiomas(),
                        s.getPais(),
                        s.getTipo(),
                        s.getPoster(),
                        s.getSinopse()))
                .collect(Collectors.toList());
    }

    // polimorfismo de sobrecarga
    public ModeloSeriePessoalDto coverteSerieParaDto(ModeloSeriePessoal modeloSeriePessoal) {
        return new ModeloSeriePessoalDto(modeloSeriePessoal.getId(),
                modeloSeriePessoal.getTitulo(),
                modeloSeriePessoal.getGenero(),
                modeloSeriePessoal.getTemporadas(),
                modeloSeriePessoal.getNota(),
                modeloSeriePessoal.getAno(),
                modeloSeriePessoal.getIdiomas(),
                modeloSeriePessoal.getPais(),
                modeloSeriePessoal.getTipo(),
                modeloSeriePessoal.getPoster(),
                modeloSeriePessoal.getSinopse());
    }

    public List<ModeloEpisodioPessoalDto> coverteEpisodioParaDto(ModeloSeriePessoal modeloSeriePessoal) {
        return modeloSeriePessoal.getEpisodios().stream()
                .map(s -> new ModeloEpisodioPessoalDto(s.getTITULO_EPISODIO(),
                        s.getNOME_SERIE(),
                        s.getNUMERO_EPISODIO(),
                        s.getTEMPORADA()))
                .collect(Collectors.toList());
    }

    public List<ModeloEpisodioPessoalDto> coverteEpisodioParaDto(List<ModeloEpisodioPessoal> modeloEpisodio) {
        return modeloEpisodio.stream()
                .map(e -> new ModeloEpisodioPessoalDto(e.getTITULO_EPISODIO(),
                        e.getNOME_SERIE(),
                        e.getNUMERO_EPISODIO(),
                        e.getTEMPORADA()))
                .collect(Collectors.toList());
    }
}

