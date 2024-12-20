package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.*;
import edu.reis.apiTreino.repository.ModeloEpisodioPessoalRepository;
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
    private ModeloSeriePessoalRepository serieRepository;
    private ModeloEpisodioPessoalRepository episodioRepository;

    public Principal(ModeloSeriePessoalRepository serieRepository, ModeloEpisodioPessoalRepository episodioRepository) {
        this.serieRepository = serieRepository;
        this.episodioRepository = episodioRepository;
    }

    //carrega uma lista dados de seções anteriores
    private void carregaListaInicialSeries() {
        seriesObjeto = serieRepository.findAll();
    }

    public void menu() {
        carregaListaInicialSeries();

        while (true) {
            String menu = """
                    
                    Escolha Uma Opção
                    
                    1- Buscar e Salvar Série - CONSUMO API.
                    
                    2- Buscar e Salvar Episódios - CONSUMO API.
                    
                    3- Listar Episódios Por Nota - OPERAÇÃO DB.
                    
                    4- Listar Séries Salvas no DB - OPERAÇÃO DB.
                    
                    5- Buscar Série Por Nome - OPERAÇÃO DB.
                    
                    6- Buscar Série Por ID - OPERAÇÃO DB.
                    
                    7- Listar as Cinco Melhores Série no DB - OPERAÇÃO DB.
                    
                    8- Buscar Série Por Categorias/Gêneros - OPERAÇÃO DB.
                    
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
                        listaEpisodiosPorNota();
                        break;
                    case 4:
                        listaSerieSalvasDb();
                        break;
                    case 5:
                        buscaSeriePorNomeNoDB();
                        break;
                    case 6:
                        buscaSeriePorIDNoDB();
                        break;
                    case 7:
                        listaAs5Melhores();
                        break;
                    case 8:
                        buscaSeriePorGenero();
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
            modeloSeriePessoal = serieRepository.save(modeloSeriePessoal);

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
                serieRepository.save(ModeloSeriePessoal);
            } else {
                System.out.println("\nA Série Escolhida Não Existe no DB");
                System.out.println("\nVocê Escolheu: " + escolha);
            }
        } else {
            System.out.println("Você Deve Buscar Uma Série Primeiro");
        }
    }

    //funciona, mas está errado, precisa melhorar
    private void listaEpisodiosPorNota() {
        System.out.println("\nInsira Uma Nota Mínima:");
        String nota = SCANNER.nextLine();

        try {
            Float notaFloat = Float.parseFloat(nota);

            List<ModeloEpisodioPessoal> melhoresEpisodios = episodioRepository.findByNotaGreaterThanEqual(notaFloat);

            melhoresEpisodios.stream()
                    .sorted(Comparator.comparing(ModeloEpisodioPessoal::getNota))
                    .forEach(e -> System.out.println(e.getTITULO_EPISODIO() +
                            " - Nota: " +e.getNota()));
        }catch (NumberFormatException e){
            System.out.println("\nErro: Insira um Número");
            System.out.println("\nVocê Escolheu: " + nota);
            System.out.println("\nErro: " + e.getMessage());
        }


    }

    private void listaSerieSalvasDb() {
        seriesObjeto = serieRepository.findAll();
        seriesObjeto.forEach(System.out::println);
    }

    private void buscaSeriePorNomeNoDB() {
        System.out.println("\n Digite o Nome ou Parte Dele Para Buscar Uma Serie no DB");
        String nomeSerie = SCANNER.nextLine();
        Optional<ModeloSeriePessoal> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            ModeloSeriePessoal serieEncontrada = serie.get();
            System.out.println(serieEncontrada);
        } else {
            System.out.println("\nSérie Não Cadastrada!");
        }
    }

    private void buscaSeriePorIDNoDB() {
        System.out.println("\n Digite o ID do Serie no DB");
        String idSerie = SCANNER.nextLine();

        try {
            Long id = Long.parseLong(idSerie);
            Optional<ModeloSeriePessoal> serie = serieRepository.findById(id);

            if (serie.isPresent()) {
                ModeloSeriePessoal serieEncontrada = serie.get();
                System.out.println(serieEncontrada);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro Com o ID do Serie: " + idSerie);
            System.out.println("Erro Com o ID do Serie: " + e.getMessage());
            System.out.println("Você Deve Informado um Número Inteiro");
        }
    }

    private String traduzir(String texto) {
        String reposta = CONSUMO_GEMINI.obterConsumo(texto);
        return reposta;
    }

    private void listaAs5Melhores() {
        List<ModeloSeriePessoal> as5Melhores = serieRepository.findTop5ByOrderByIdDesc();
        as5Melhores.stream()
                .forEach(s -> System.out.println(s.getTitulo() +
                        " - Nota: "+ s.getNota()));
    }

    private void buscaSeriePorGenero() {
        System.out.println("\n Digite o Gênero da Serie");
        String generoInserido = SCANNER.nextLine();
        GenerosEnum genero = GenerosEnum.fromBuscaEmPortugues(generoInserido);
        List<ModeloSeriePessoal> seriePorGenero = serieRepository.findByGenero(genero);
        seriePorGenero.stream()
                .forEach(s -> System.out.println(s.getTitulo() +
                        " - Nota: "+ s.getNota() +
                        " - Gênero: " + s.getGenero()));
    }
}
