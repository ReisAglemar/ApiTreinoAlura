package edu.reis.apiTreino.service;

import edu.reis.apiTreino.dto.ModeloSeriePessoalDto;
import edu.reis.apiTreino.repository.ModeloSeriePessoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloSeriePessoalService {

    @Autowired
    private ModeloSeriePessoalRepository modeloSeriePessoalRepository;

    public List<ModeloSeriePessoalDto> buscarSeriePessoal() {

        return modeloSeriePessoalRepository.findAll().stream()
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
