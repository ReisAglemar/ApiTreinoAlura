package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.BuscaSerie;
import edu.reis.apiTreino.model.ListaEpisodio;
import edu.reis.apiTreino.model.ModeloEpisodioPessoal;
import edu.reis.apiTreino.model.ModeloSeriePessoal;
import edu.reis.apiTreino.repository.ModeloSeriePessoalRepository;
import edu.reis.apiTreino.service.ConsumoAPIGemini;
import edu.reis.apiTreino.service.ConsumoAPIOmdb;
import edu.reis.apiTreino.service.ConverteJsonClasse;
import org.hibernate.event.spi.SaveOrUpdateEvent;

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
    private final ConsumoAPIOmdb CONSUMO_OMDB = new ConsumoAPIOmdb();
    private final ConverteJsonClasse CONVERSOR = new ConverteJsonClasse();
    private final ConsumoAPIGemini CONSUMO_GEMINI = new ConsumoAPIGemini();
    private BuscaSerie buscaSerie;
    private String titulo;
    private String linkApi;
    private String json;
    private final List<BuscaSerie> series = new ArrayList<>();
    private final List<ListaEpisodio> episodios = new ArrayList<>();
    private List<ModeloEpisodioPessoal> episodiosObjeto = new ArrayList<>();
    private List<ModeloSeriePessoal> seriesObjeto = new ArrayList<>();
    private ModeloSeriePessoalRepository repository;

    public Principal(ModeloSeriePessoalRepository repository) {
        this.repository = repository;
    }


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
        this.json = CONSUMO_OMDB.obterConsumo(linkApi);
        this.buscaSerie = CONVERSOR.converteTipos(json, BuscaSerie.class);
        return buscaSerie;
    }


    private void buscaTodosEpisodio(BuscaSerie busca) {

        for (int i = 1; i <= Integer.parseInt(busca.temporadas()); i++) {

            this.linkApi = DOMINIO + titulo.replace(" ", "+") + SEASON + i + API_KEY;
            this.json = CONSUMO_OMDB.obterConsumo(linkApi);
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
                .peek(s -> s.setSinopse("Traduzido Por Google Gemini: " + traduzir(s.getSinopse())))
                .map(s-> repository.save(s))
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


    private String traduzir(String texto) {

        String reposta = CONSUMO_GEMINI.obterConsumo(texto);
        return reposta;
    }
}
