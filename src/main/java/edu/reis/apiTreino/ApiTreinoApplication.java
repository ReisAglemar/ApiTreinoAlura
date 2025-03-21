package edu.reis.apiTreino;

import edu.reis.apiTreino.principal.Principal;
import edu.reis.apiTreino.repository.ModeloEpisodioPessoalRepository;
import edu.reis.apiTreino.repository.ModeloSeriePessoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;

@SpringBootApplication
public class ApiTreinoApplication implements CommandLineRunner {

    @Autowired
    private ModeloSeriePessoalRepository serieRepository;

    @Autowired
    private ModeloEpisodioPessoalRepository episodioRepository;

    private final ProjectInfoAutoConfiguration projectInfoAutoConfiguration;

    public ApiTreinoApplication(ProjectInfoAutoConfiguration projectInfoAutoConfiguration) {
        this.projectInfoAutoConfiguration = projectInfoAutoConfiguration;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiTreinoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Principal principal = new Principal(serieRepository, episodioRepository);
        principal.menu();

    }
}


