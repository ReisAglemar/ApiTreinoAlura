package edu.reis.apiTreino.repository;

import edu.reis.apiTreino.model.ModeloEpisodioPessoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloEpisodioPessoalRepository extends JpaRepository<ModeloEpisodioPessoal, Long> {

    List<ModeloEpisodioPessoal> findByNotaGreaterThanEqual(float nota);
}
