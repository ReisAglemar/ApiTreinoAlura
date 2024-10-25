package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.Serie;
import edu.reis.apiTreino.model.TodosEpisodioSerie;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String DOMINIO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b53950be";
    private final String SEASON = "&season=";
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverteJsonClasse conversor = new ConverteJsonClasse();
    private Serie serie;
    private String titulo;
    private String linkApi;
    private String json;
    private List<TodosEpisodioSerie> episodios = new ArrayList<>();

    public void insiraTitulo() {
        System.out.print("Digite um titulo para pesquisar: ");
        titulo = scanner.nextLine();
    }


    public void buscarSerie() {

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + API_KEY;
        this.json = consumo.obterConsumo(linkApi);
        this.serie = conversor.converteTipos(json, Serie.class);
        System.out.println(serie.toString());
    }


    public void buscarTodosEpisodio() {

        for (int i = 1; i <= Integer.parseInt(serie.temporadas()); i++) {
            this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + i + API_KEY;
            this.json = consumo.obterConsumo(linkApi);
            TodosEpisodioSerie todosEpisodioSerie = conversor.converteTipos(json, TodosEpisodioSerie.class);
            this.episodios.add(todosEpisodioSerie);
        }

        this.episodios.forEach(System.out::println);
    }


    public void buscarEpisodioTemporadaEspecifica(int episodio) {

        boolean estaNoIntevalo = episodio > 0 && episodio <= Integer.parseInt(serie.temporadas());

        if (!estaNoIntevalo) {
            System.out.println("O episódio deve ser maior que 0 e menor ou igual a " + serie.temporadas());
            System.out.println("Vocẽ informou: " + episodio);
            return;
        }

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + episodio + API_KEY;
        this.json = consumo.obterConsumo(linkApi);
        TodosEpisodioSerie todosEpisodioSerie = conversor.converteTipos(json, TodosEpisodioSerie.class);
        System.out.println(todosEpisodioSerie);
    }
}
