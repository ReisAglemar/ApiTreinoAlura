package edu.reis.apiTreino.controller;

import edu.reis.apiTreino.dto.ModeloSeriePessoalDto;
import edu.reis.apiTreino.service.ModeloSeriePessoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModeloSeriePessoalController {

    @Autowired
    private ModeloSeriePessoalService service;

    @GetMapping("/series")
    public List<ModeloSeriePessoalDto> obtemModeloSeriePessoal() {
        return service.buscarSeriePessoal();
    }
}
