package edu.reis.apiTreino.service;

import edu.reis.apiTreino.dto.ModeloEpisodioPessoalDto;
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
        return converteParaDto.coverteSerieParaDto(modeloSeriePessoalRepository.findAll());
    }

    public List<ModeloSeriePessoalDto> buscarMelhorSeriePessoal() {
        return converteParaDto.coverteSerieParaDto(modeloSeriePessoalRepository.findTop5ByOrderByIdDesc());
    }

    public List<ModeloSeriePessoalDto> buscarLancamentosSeriePessoal() {
        return converteParaDto.coverteSerieParaDto(modeloSeriePessoalRepository.lancamentos());
    }

    public ModeloSeriePessoalDto buscarSeriePessoalPorId(Long id) {
        Optional<ModeloSeriePessoal> modeloSeriePessoal = modeloSeriePessoalRepository.findById(id);

        if (modeloSeriePessoal.isPresent()) {
            return converteParaDto.coverteSerieParaDto(modeloSeriePessoal.get());
        }
        return null;
    }

    public List<ModeloEpisodioPessoalDto> bucarEpisodioPessoal(Long id) {
        Optional<ModeloSeriePessoal> modeloSeriePessoal = modeloSeriePessoalRepository.findById(id);

        if (modeloSeriePessoal.isPresent()) {
            return converteParaDto.coverteEpisodioParaDto(modeloSeriePessoal.get());
        }
        return null;
    }

    public List<ModeloEpisodioPessoalDto> buscarEpisodiosTemporada(Long id, Integer numero) {
        return converteParaDto.coverteEpisodioParaDto(modeloSeriePessoalRepository.buscarEpisodioTemporada(id, numero));
    }
}
