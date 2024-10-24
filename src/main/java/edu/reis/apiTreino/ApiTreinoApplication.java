package edu.reis.apiTreino;

import edu.reis.apiTreino.model.EpisodioEspecifico;
import edu.reis.apiTreino.model.Serie;
import edu.reis.apiTreino.model.TodosEpesodioSerie;
import edu.reis.apiTreino.principal.Principal;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiTreinoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiTreinoApplication.class, args);
    }

    @Override
    public void run(String... args) {


        Principal principal = new Principal();
        principal.buscarSerie();

//        //pesquisa uma série, info básicas
//
//        ConverteJsonClasse converte = new ConverteJsonClasse();
//
//        Serie dadoSerie = converte.converteTipos(json, Serie.class);
//        System.out.println(dadoSerie);
//
//        //pesquisa por um episódio específico
//
//
//        EpisodioEspecifico dadoEpisodioEspecifico = converte.converteTipos(json, EpisodioEspecifico.class);
//        System.out.println(dadoEpisodioEspecifico);
//
//        List<TodosEpesodioSerie> episodios = new ArrayList<>();
//
//        for (int i = 1; i <= Integer.parseInt(dadoSerie.temporadas()); i++) {
//
//            TodosEpesodioSerie todosEpesodioSerie = converte.converteTipos(json, TodosEpesodioSerie.class);
//            episodios.add(todosEpesodioSerie);
//        }
//
//        episodios.forEach(System.out::println);

    }
}


