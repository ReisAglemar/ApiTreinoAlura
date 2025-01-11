package edu.reis.apiTreino.repository;

import edu.reis.apiTreino.model.GenerosEnum;
import edu.reis.apiTreino.model.ModeloEpisodioPessoal;
import edu.reis.apiTreino.model.ModeloSeriePessoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ModeloSeriePessoalRepository extends JpaRepository<ModeloSeriePessoal, Long> {

    Optional<ModeloSeriePessoal> findByTituloContainingIgnoreCase(String titulo);

    Optional<ModeloSeriePessoal> findById(Long id);

    List<ModeloSeriePessoal> findTop5ByOrderByIdDesc();

    List<ModeloSeriePessoal> findByGenero(GenerosEnum genero);

    @Query("SELECT e FROM ModeloSeriePessoal s JOIN s.episodios e WHERE e.TITULO_EPISODIO ILIKE %:trecho%")
    List<ModeloEpisodioPessoal> buscaEpisodioPorTrecho(String trecho);

        List<ModeloSeriePessoal> findTop5ByOrderByEpisodiosDataLancamentoDesc();
}
