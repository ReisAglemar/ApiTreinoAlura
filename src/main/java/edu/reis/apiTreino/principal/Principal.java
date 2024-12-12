package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.BuscaSerie;
import edu.reis.apiTreino.model.ListaEpisodio;
import edu.reis.apiTreino.model.ModeloEpisodioPessoal;
import edu.reis.apiTreino.model.ModeloSeriePessoal;
import edu.reis.apiTreino.repository.ModeloSeriePessoalRepository;
import edu.reis.apiTreino.service.ConsumoAPIGemini;
import edu.reis.apiTreino.service.ConsumoAPIOmdb;
import edu.reis.apiTreino.service.ConverteJsonClasse;

import java.util.*;
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
    private final List<ListaEpisodio> episodios = new ArrayList<>();
    private List<ModeloEpisodioPessoal> episodiosObjeto = new ArrayList<>();
    private List<ModeloSeriePessoal> seriesObjeto = new ArrayList<>();
    private ModeloSeriePessoalRepository repository;

    public Principal(ModeloSeriePessoalRepository repository) {
        this.repository = repository;
    }

    //carrega uma lista dados de seções anteriores
    private void carregaListaInicialSeries() {
        seriesObjeto = repository.findAll();
    }

    public void menu() {
        carregaListaInicialSeries();

        while (true) {
            String menu = """
                    
                    Escolha Uma Opção
                    
                    1- Buscar e Salvar Série.
                    
                    2- Buscar e Salvar Episódios.
                    
                    3- Ver os Melhores Episódios de Uma Série(NOT).
                    
                    4- Listar Séries Salvas no DB.
                    
                    5- Listar Episódios Buscados(NOT).
                    
                    0- Para Sair.
                    
                    """;
            System.out.println(menu);

            try {
                int opcao = Integer.parseInt(SCANNER.nextLine());
                switch (opcao) {
                    case 1:
                        insiraTitulo();
                        System.out.println(salvaSerieDb());
                        break;

                    case 2:
                        buscaTodosEpisodio();
                        break;

                    case 3:
                        melhoresEpisodios();
                        break;

                    case 4:
                        listaSerieSalvasDb();
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

    private String salvaSerieDb() {

        try {

            ModeloSeriePessoal modeloSeriePessoal = new ModeloSeriePessoal(buscaSerie());

            modeloSeriePessoal.setSinopse("Traduzido Por Google Gemini: " + traduzir(modeloSeriePessoal.getSinopse()));
            modeloSeriePessoal = repository.save(modeloSeriePessoal);

            if (modeloSeriePessoal == null) {
                return "\nErro Ao Salvar Série: " + modeloSeriePessoal.getTitulo();
            }

        } catch (Exception e) {
            return "\nErro: " + e.getMessage();
        }
        return "\nSérie Salva Com Sucesso!\n";
    }

    private BuscaSerie buscaSerie() {

        this.linkApi = DOMINIO + titulo.replace(" ", "+") + API_KEY;
        this.json = CONSUMO_OMDB.obterConsumo(linkApi);
        this.buscaSerie = CONVERSOR.converteTipos(json, BuscaSerie.class);
        return buscaSerie;
    }

    private void buscaTodosEpisodio() {

        if (!seriesObjeto.isEmpty()) {
            listaSerieSalvasDb();
            System.out.println("\nEscolha Uma Série Disponível");
            String escolha = SCANNER.nextLine();

            Optional<ModeloSeriePessoal> isSerieEncontrada = seriesObjeto.stream()
                    .filter(s -> s.getTitulo().toLowerCase().contains(escolha.toLowerCase()))
                    .findFirst();

            var ModeloSeriePessoal = isSerieEncontrada.get();

            if (isSerieEncontrada.isPresent()) {
                for (int i = 1; i <= ModeloSeriePessoal.getTemporadas(); i++) {
                    this.linkApi = DOMINIO + escolha.replace(" ", "+") + SEASON + i + API_KEY;
                    this.json = CONSUMO_OMDB.obterConsumo(linkApi);
                    ListaEpisodio listaEpisodio = CONVERSOR.converteTipos(json, ListaEpisodio.class);
                    episodios.add(listaEpisodio);
                }

                episodiosObjeto = episodios.stream()
                        .flatMap(e -> e.episodios().stream()
                                .map(o -> new ModeloEpisodioPessoal(e.temporada(), e.nomeSerie(), o)))
                        .collect(Collectors.toList());

                ModeloSeriePessoal.setEpisodios(episodiosObjeto);
                repository.save(ModeloSeriePessoal);
            } else {
                System.out.println("\nA Série Escolhida Não Existe no DB");
                System.out.println("\nVocê Escolheu: " + escolha);
            }
        } else {
            System.out.println("Você Deve Buscar Uma Série Primeiro");
        }
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

    private void listaSerieSalvasDb() {
        seriesObjeto = repository.findAll();
        seriesObjeto.forEach(System.out::println);
    }

    private void listaEpisodioBuscadas() {
        episodiosObjeto.forEach(System.out::println);
    }

    private String traduzir(String texto) {
        String reposta = CONSUMO_GEMINI.obterConsumo(texto);
        return reposta;
    }
}
