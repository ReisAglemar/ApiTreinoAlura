package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.BuscaSerie;
import edu.reis.apiTreino.model.ListaEpisodio;
import edu.reis.apiTreino.model.ModeloEpisodioPessoal;
import edu.reis.apiTreino.model.ModeloSeriePessoal;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;

import java.util.*;
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
    private List<ListaEpisodio> episodios = new ArrayList<>();
    private List<ModeloEpisodioPessoal> episodiosObjeto = new ArrayList<>();
    private List<ModeloSeriePessoal> seriesObjeto = new ArrayList<>();
    private List<BuscaSerie> series = new ArrayList<>();


    public void menu() {

        while (true) {
            String menu = """
                    
                    Escolha Uma Opção
                    
                    1- Buscar Série.
                    
                    2- Buscar Todos os Episódios de Uma Série.
                    
                    3- Ver os Melhores Episódios de Uma Série.
                    
                    4- Listar Séries Buscadas.
                    
                    5- Listar Episódios Buscados.
                    
                    0- Para Sair.
                    
                    """;

            System.out.println(menu);


            try {
                int opcao = Integer.parseInt(SCANNER.nextLine());
                switch (opcao) {
                    case 1:
                        insiraTitulo();
                        series.add(buscaSerie());
                        break;

                    case 2:
                        insiraTitulo();
                        buscaTodosEpisodio(buscaSerie());
                        break;

                    case 3:
                        melhoresEpisodios();
                        break;

                    case 4:
                        listaSerieBuscadas();
                        break;

                    case 5:
                        listaEpisodioBuscadas();
                        break;

                    case 0:
                        System.out.println("Fim do Programa");
                        return;

                    default:
                        System.out.println("ERRO: Opção Inválida");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Você Deve Inserir um Número Inteiro");
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }

    }


    private void insiraTitulo() {
        System.out.print("\n\nDigite um titulo para pesquisar: ");
        this.titulo = SCANNER.nextLine();
    }


    private BuscaSerie buscaSerie() {

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + API_KEY;
        this.json = CONSUMO.obterConsumo(linkApi);
        this.buscaSerie = CONVERSOR.converteTipos(json, BuscaSerie.class);
        return buscaSerie;
    }


    private void buscaTodosEpisodio(BuscaSerie busca) {

        for (int i = 1; i <= Integer.parseInt(busca.temporadas()); i++) {

            this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + i + API_KEY;
            this.json = CONSUMO.obterConsumo(linkApi);
            ListaEpisodio listaEpisodio = CONVERSOR.converteTipos(json, ListaEpisodio.class);
            episodios.add(listaEpisodio);
        }

        episodiosObjeto = episodios.stream()
                .flatMap(e -> e.episodios().stream()
                        .map(o -> new ModeloEpisodioPessoal(e.temporada(), e.nomeSerie(), o)))
                .collect(Collectors.toList());
    }

    private void listaSerieBuscadas() {

        //cria objetos usando stream
        seriesObjeto = series.stream()
                        .map(s -> new ModeloSeriePessoal(s))
                                .collect(Collectors.toList());

        // ordenar os objetos por nota(maior para menor) e imprime
        seriesObjeto.stream()
                .sorted(Comparator.comparing(ModeloSeriePessoal::getNota).reversed())
                .forEach(System.out::println);
    }

    private void listaEpisodioBuscadas() {
        episodiosObjeto.forEach(System.out::println);
    }

    private void melhoresEpisodios() {


        if (!episodiosObjeto.isEmpty()) {

            System.out.println("Insira a Nota de Corte");
            int nota = Integer.parseInt(SCANNER.nextLine());

            episodiosObjeto.stream()
                    .filter(o -> o.getNota() > nota)
                    .sorted(Comparator.comparing(ModeloEpisodioPessoal::getNota))
                    .forEach(System.out::println);
            return;
        }

        System.out.println("Lista vazia! Faça Uma Busca de Episódios");
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

//    private void dezMelhoresEpisodioTratado() {
//
//        episodiosObjeto = episodios.stream()
//                .flatMap(e -> e.episodios().stream()
//                        .map(o -> new ModeloEpisodioPessoal(e.temporada(), o)))
//                .collect(Collectors.toList());
//
//
//        episodiosObjeto.stream()
//                .filter(n -> n.getNota() > 0.0f)
//                .sorted(Comparator.comparing(ModeloEpisodioPessoal::getNota).reversed())
//                .limit(10)
//                .forEach(System.out::println);
//    }

    public void buscaEpisodioDentreDezMaisTratado() {

        System.out.print("Digite o nome do Episodio: ");
        String busca = SCANNER.nextLine();

        Optional<ModeloEpisodioPessoal> episodio = episodiosObjeto.stream()
                .filter(t -> t.getTITULO_EPISODIO().toLowerCase().contains(busca.toLowerCase()))
                .findFirst();

        if (episodio.isPresent()) {
            System.out.println(episodio.get().toString());
            return;
        }

        System.out.println("Episódio não encontrado");
    }

    public void buscaEpisodiosTemporadaEspecifica(int temporada) {

        if (buscaSerie == null) {
            System.out.println("Vocẽ deve primeiro buscar por uma série");
            return;
        }

        boolean intervaloValido = temporada > 0 && temporada <= Integer.parseInt(buscaSerie.temporadas());

        if (!intervaloValido) {
            System.out.println("O episódio deve ser maior que 0 e menor ou igual a " + buscaSerie.temporadas());
            System.out.println("Vocẽ informou: " + temporada);
            return;
        }

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + temporada + API_KEY;
        this.json = CONSUMO.obterConsumo(linkApi);
        ListaEpisodio listaEpisodio = CONVERSOR.converteTipos(json, ListaEpisodio.class);
        System.out.println(listaEpisodio);
    }


    public void dadosEstatistico() {

        DoubleSummaryStatistics estatistica = episodiosObjeto.stream()
                .filter(e -> e.getNota() > 0)
                .collect(Collectors.summarizingDouble(ModeloEpisodioPessoal::getNota));

        String saida = """
                
                ============= Dados Estatísticos =============
                
                    Média Das Notas: %.1f
                    Nota Máxima: %.1f
                    Nota Minima: %.1f
                    Total de Episódios Avaliados: %d
                
                """.formatted(estatistica.getAverage(), estatistica.getMax(), estatistica.getMin(), estatistica.getCount());

        System.out.println(saida);
    }
}
