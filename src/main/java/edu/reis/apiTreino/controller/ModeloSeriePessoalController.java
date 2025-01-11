package edu.reis.apiTreino.controller;

import edu.reis.apiTreino.dto.ModeloSeriePessoalDto;
import edu.reis.apiTreino.service.ModeloSeriePessoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class ModeloSeriePessoalController {

    @Autowired
    private ModeloSeriePessoalService service;

    @GetMapping
    public List<ModeloSeriePessoalDto> obtemModeloSeriePessoal() {
        return service.buscarSeriePessoal();
    }

    @GetMapping("/top5")
    public List<ModeloSeriePessoalDto> obtemModeloSeriePessoalTop() {
        return service.buscarMelhorSeriePessoal();
    }

    @GetMapping("/lancamentos")
    public List<ModeloSeriePessoalDto> lancamentos() {
        return service.buscarSeriePessoalLancamentos();
    }

}
