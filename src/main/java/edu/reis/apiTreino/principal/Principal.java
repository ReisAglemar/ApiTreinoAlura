package edu.reis.apiTreino.principal;

import edu.reis.apiTreino.model.Serie;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;

import java.util.Scanner;

public class Principal {

    private final String DOMINIO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b53950be";
    private Scanner scanner = new Scanner(System.in);
    ConsumoAPI consumo = new ConsumoAPI();
    ConverteJsonClasse conversor = new ConverteJsonClasse();


    public void buscarSerie() {
        System.out.print("Digite o nome do serie: ");
        String nome = scanner.nextLine();
        String linkApi = DOMINIO + nome.replace(" ", "+") + API_KEY;
        System.out.println(linkApi);
        String json = consumo.obterConsumo(linkApi);
        Serie serie = conversor.converteTipos(json, Serie.class);
        System.out.println(serie.toString());
    }


}
