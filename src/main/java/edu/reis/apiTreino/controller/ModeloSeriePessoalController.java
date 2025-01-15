package edu.reis.apiTreino.controller;

import edu.reis.apiTreino.dto.ModeloEpisodioPessoalDto;
import edu.reis.apiTreino.dto.ModeloSeriePessoalDto;
import edu.reis.apiTreino.service.ModeloSeriePessoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class ModeloSeriePessoalController {

    @Autowired
    private ModeloSeriePessoalService service;

    @GetMapping
    public List<ModeloSeriePessoalDto> obtemModeloSeriePessoalLista() {
        return service.buscarSeriePessoal();
    }

    @GetMapping("/top5")
    public List<ModeloSeriePessoalDto> obtemModeloSeriePessoalTop5() {
        return service.buscarMelhorSeriePessoal();
    }

    @GetMapping("/lancamentos")
    public List<ModeloSeriePessoalDto> obtemModeloSeriePessoalLancamentos() {
        return service.buscarLancamentosSeriePessoal();
    }

    @GetMapping("/{id}")
    public ModeloSeriePessoalDto obtemModeloSeriePessoalPorId(@PathVariable Long id) {
        return service.buscarSeriePessoalPorId(id);
    }

    @GetMapping("{id}/temporadas/todas")
    public List<ModeloEpisodioPessoalDto> obtemModeloEpisodioPessoalTodos(@PathVariable Long id) {
        return service.bucarEpisodioPessoal(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<ModeloEpisodioPessoalDto> obtemModeloEpisodioPessoalPorTemporada(@PathVariable Long id, @PathVariable Integer numero) {
        return service.buscarEpisodiosTemporada(id, numero);
    }


}
