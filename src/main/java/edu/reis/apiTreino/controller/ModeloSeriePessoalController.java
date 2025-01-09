package edu.reis.apiTreino.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModeloSeriePessoalController {

    @GetMapping("/series")
    public String obtemSeries() {
        return "Tu é monstro!!!!";
    }
}
