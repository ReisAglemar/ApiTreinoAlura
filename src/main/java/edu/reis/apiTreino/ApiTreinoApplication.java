package edu.reis.apiTreino;

import edu.reis.apiTreino.model.Episodio;
import edu.reis.apiTreino.model.Serie;
import edu.reis.apiTreino.service.ConsumoAPI;
import edu.reis.apiTreino.service.ConverteJsonClasse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiTreinoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiTreinoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        ConsumoAPI consumo = new ConsumoAPI();

        String linkApi = "https://www.omdbapi.com/?t=lost&apikey=b53950be";
        String json = consumo.obterConsumo(linkApi);

        ConverteJsonClasse converte = new ConverteJsonClasse();

        Serie dadoSerie = converte.converteTipos(json, Serie.class);
        System.out.println(dadoSerie);

        linkApi = "https://www.omdbapi.com/?type=series&t=lost&season=2&episode=2&apikey=b53950be";
        json = consumo.obterConsumo(linkApi);

        Episodio dadoEpisodio = converte.converteTipos(json, Episodio.class);
        System.out.println(dadoEpisodio);

    }
}


