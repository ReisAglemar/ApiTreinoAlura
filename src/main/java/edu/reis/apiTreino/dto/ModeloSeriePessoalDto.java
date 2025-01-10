package edu.reis.apiTreino.dto;

import edu.reis.apiTreino.model.GenerosEnum;

public record ModeloSeriePessoalDto(long id,
                                    String titulo,
                                    GenerosEnum genero,
                                    Integer temporadas,
                                    Double nota,
                                    String ano,
                                    String idiomas,
                                    String pais,
                                    String tipo,
                                    String poster,
                                    String sinopse) {
}
