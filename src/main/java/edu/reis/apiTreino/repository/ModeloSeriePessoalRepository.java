package edu.reis.apiTreino.repository;

import edu.reis.apiTreino.model.ModeloSeriePessoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeloSeriePessoalRepository extends JpaRepository<ModeloSeriePessoal, Long> {

    Optional<ModeloSeriePessoal> findByTituloContainingIgnoreCase(String titulo);
    Optional<ModeloSeriePessoal> findById(Long id);
}
