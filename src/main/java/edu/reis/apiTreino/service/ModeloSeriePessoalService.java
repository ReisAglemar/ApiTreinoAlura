package edu.reis.apiTreino.service;

import edu.reis.apiTreino.dto.ModeloSeriePessoalDto;
import edu.reis.apiTreino.model.ModeloSeriePessoal;
import edu.reis.apiTreino.repository.ModeloSeriePessoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloSeriePessoalService {

    @Autowired
    private ModeloSeriePessoalRepository modeloSeriePessoalRepository;

    @Autowired
    private ConverteParaDto converteParaDto;

    public List<ModeloSeriePessoalDto> buscarSeriePessoal() {
        return converteParaDto.ConverteParaDto(modeloSeriePessoalRepository.findAll());
    }

    public List<ModeloSeriePessoalDto> buscarMelhorSeriePessoal() {
        return converteParaDto.ConverteParaDto(modeloSeriePessoalRepository.findTop5ByOrderByIdDesc());
    }

    public List<ModeloSeriePessoalDto> buscarSeriePessoalLancamentos() {
        return converteParaDto.ConverteParaDto(modeloSeriePessoalRepository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }

    public ModeloSeriePessoalDto buscarSeriePessoalPorId(Long id) {
        Optional<ModeloSeriePessoal> modeloSeriePessoal = modeloSeriePessoalRepository.findById(id);

        if (modeloSeriePessoal.isPresent()) {
            return converteParaDto.ConverteParaDto(modeloSeriePessoal.get());
        }
        return null;
    }
}
