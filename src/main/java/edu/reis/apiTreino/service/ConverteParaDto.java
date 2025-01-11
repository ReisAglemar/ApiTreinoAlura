package edu.reis.apiTreino.service;

import edu.reis.apiTreino.dto.ModeloSeriePessoalDto;
import edu.reis.apiTreino.model.ModeloSeriePessoal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverteParaDto {

    public ConverteParaDto() {
    }

    public List<ModeloSeriePessoalDto> ConverteParaDto(List<ModeloSeriePessoal> modeloSeriePessoal) {
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
}

