package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.BuscaSerie;
import edu.reis.apiTreino.model.ListaEpisodio;
import edu.reis.apiTreino.model.ModeloEpisodioPessoal;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String DOMINIO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b53950be";
    private final String SEASON = "&season=";
    private final Scanner SCANNER = new Scanner(System.in);
    private final ConsumoAPI CONSUMO = new ConsumoAPI();
    private final ConverteJsonClasse CONVERSOR = new ConverteJsonClasse();
    private BuscaSerie buscaSerie;
    private String titulo;
    private String linkApi;
    private String json;
    private final List<ListaEpisodio> TEMPORADAS = new ArrayList<>();

    public void insiraTitulo() {
        System.out.print("\n\nDigite um titulo para pesquisar: ");
        this.titulo = SCANNER.nextLine();
        buscaSerie();
    }


    private void buscaSerie() {

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + API_KEY;
        this.json = CONSUMO.obterConsumo(linkApi);
        this.buscaSerie = CONVERSOR.converteTipos(json, BuscaSerie.class);
        System.out.println(buscaSerie.toString());
        buscaTodosEpisodio();
    }


    private void buscaTodosEpisodio() {

        for (int i = 1; i <= Integer.parseInt(buscaSerie.temporadas()); i++) {
            this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + i + API_KEY;
            this.json = CONSUMO.obterConsumo(linkApi);
            ListaEpisodio listaEpisodio = CONVERSOR.converteTipos(json, ListaEpisodio.class);
            this.TEMPORADAS.add(listaEpisodio);
        }

        System.out.println("======== Resultado Para Todos os Episódios de Todas as Temporadas ========\n");
        TEMPORADAS.forEach(System.out::println);
        dezMelhoresEpisodioTratado();
    }


//    private void dezMelhoresEpisodios() {
//
//        System.out.println("======== Resultado Para os Dez Melhores Episodios ========\n");
//
//        /*
//         * Temporada é lista das temporadas que conte os seus episódios
//         * cada episódio tem uma gama de informações, sendo assim é uma lista dentro de temporada.
//         *
//         * Temporada lista que contem uma lista de episódios.
//         * Lista dentro de lista.
//         *
//         * Para operar sobre o episódio em específico recomenda-se separar em outra lista de apenas
//         * episódio
//         * */
//
//        // extrair a lista interna
//        List<ModeloEpisodio> apenasEpisodio = temporadas.stream()
//                .flatMap(e -> e.episodios().stream())
//                .collect(Collectors.toList());
//
//
//        // opera sobre a nova lista, essa era a lista interna da lista de temporadas.
//        apenasEpisodio.stream()
//                .filter(n -> !n.nota().equalsIgnoreCase("n/a"))
//                .sorted(Comparator.comparing(ModeloEpisodio::nota).reversed())
//                .limit(10)
//                .forEach(System.out::println);
//    }
//

    private void dezMelhoresEpisodioTratado() {

        List<ModeloEpisodioPessoal> apenasEpisodioTrataddos = TEMPORADAS.stream()
                .flatMap(e -> e.episodios().stream()
                        .map(o -> new ModeloEpisodioPessoal(e.temporada(), o)))
                .collect(Collectors.toList());


        apenasEpisodioTrataddos.stream()
                .filter(n -> n.getNota() > 0.0f)
                .sorted(Comparator.comparing(ModeloEpisodioPessoal::getNota).reversed())
                .limit(10)
                .forEach(System.out::println);
    }


    public void buscaEpisodioTemporadaEspecifica(int episodio) {

        if (buscaSerie == null) {
            System.out.println("Vocẽ deve primeiro buscar por uma série");
            return;
        }

        boolean intervaloValido = episodio > 0 && episodio <= Integer.parseInt(buscaSerie.temporadas());

        if (!intervaloValido) {
            System.out.println("O episódio deve ser maior que 0 e menor ou igual a " + buscaSerie.temporadas());
            System.out.println("Vocẽ informou: " + episodio);
            return;
        }

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + episodio + API_KEY;
        this.json = CONSUMO.obterConsumo(linkApi);
        ListaEpisodio listaEpisodio = CONVERSOR.converteTipos(json, ListaEpisodio.class);
        System.out.println(listaEpisodio);
    }
}
