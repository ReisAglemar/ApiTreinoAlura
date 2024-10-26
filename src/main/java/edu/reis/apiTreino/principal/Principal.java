package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.EpisodiosTemporadaEspecifica;
import edu.reis.apiTreino.model.Serie;
import edu.reis.apiTreino.model.TodosEpisodioSerie;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;

import java.util.*;
import java.util.stream.Collectors;

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
    private List<TodosEpisodioSerie> temporadas = new ArrayList<>();

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
            this.temporadas.add(todosEpisodioSerie);
        }

        System.out.println("======== Resultado Para Todos os Episódios de Todas as Temporadas ========\n");
        temporadas.forEach(System.out::println);
        dezMelhoresEpisodios();
    }


    private void dezMelhoresEpisodios() {

        System.out.println("======== Resultado Para os Dez Melhores Episodios ========\n");

        /*
         * Temporada é lista das temporadas que conte os seus episódios
         * cada episódio tem uma gama de informações, sendo assim é uma lista dentro de temporada.
         *
         * Temporada lista que contem uma lista de episódios.
         * Lista dentro de lista.
         *
         * Para operar sobre o episódio em específico recomenda-se separar em outra lista de apenas
         * episódio
         * */

        // extrair a lista interna
        List<EpisodiosTemporadaEspecifica> apenasEpisodio = temporadas.stream()
                .flatMap(e -> e.episodios().stream())
                .collect(Collectors.toList());


        // opera sobre a nova lista, essa era a lista interna da lista de temporadas.
        apenasEpisodio.stream()
                .filter(n -> !n.nota().equalsIgnoreCase("n/a"))
                .sorted(Comparator.comparing(EpisodiosTemporadaEspecifica::nota).reversed())
                .limit(10)
                .forEach(System.out::println);


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


}
