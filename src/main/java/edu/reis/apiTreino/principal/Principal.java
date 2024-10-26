package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.Serie;
import edu.reis.apiTreino.model.TodosEpisodioSerie;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

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
        System.out.print("\n\nDigite um titulo para pesquisar: ");
        this.titulo = scanner.nextLine();
        buscarSerie();
    }


    private void buscarSerie() {

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + API_KEY;
        this.json = consumo.obterConsumo(linkApi);
        this.serie = conversor.converteTipos(json, Serie.class);
        System.out.println(serie.toString());
        buscarTodosEpisodio();
    }


    private void buscarTodosEpisodio() {

        for (int i = 1; i <= Integer.parseInt(serie.temporadas()); i++) {
            this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + i + API_KEY;
            this.json = consumo.obterConsumo(linkApi);
            TodosEpisodioSerie todosEpisodioSerie = conversor.converteTipos(json, TodosEpisodioSerie.class);
            this.episodios.add(todosEpisodioSerie);
        }

        System.out.println("======== Resultado Para Todos os Episódios de Todas as Temporadas ========\n");

        episodios.stream()
                .forEach(System.out::println);

//        episodios.forEach(System.out::println);
    }


    public void buscarEpisodioTemporadaEspecifica(int episodio) {

        if (serie == null) {
            System.out.println("Vocẽ deve primeiro buscar por uma série");
            return;
        }

        boolean intervaloValido = episodio > 0 && episodio <= Integer.parseInt(serie.temporadas());

        if (!intervaloValido) {
            System.out.println("O episódio deve ser maior que 0 e menor ou igual a " + serie.temporadas());
            System.out.println("Vocẽ informou: " + episodio);
            return;
        }

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + episodio + API_KEY;
        this.json = consumo.obterConsumo(linkApi);
        TodosEpisodioSerie todosEpisodioSerie = conversor.converteTipos(json, TodosEpisodioSerie.class);
        System.out.println(todosEpisodioSerie);
    }

    public void treinoStream() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numeros.stream()
                .forEach(n -> {
                    System.out.println("Tabuada do " + n);
                    IntStream.range(0, 10)
                            .forEach(i -> {
                                System.out.println(n + " X " + i + " = " + n * i);
                            });
                });

        for (int i = 0; i <= numeros.size(); i++) {
            System.out.println("Tabuada do " + i);
            for (int j = 0; j < numeros.size(); j++) {
                System.out.println(i + " X " + j + " = "+ i * j);
            }
        }
    }

}
