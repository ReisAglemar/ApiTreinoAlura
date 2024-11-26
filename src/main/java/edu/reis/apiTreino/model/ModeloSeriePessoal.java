package edu.reis.apiTreino.model;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class ModeloSeriePessoal {

    private final String titulo;
    private final GenerosEnum genero;
    private final Integer temporadas;
    private final Double nota;
    private final String ano;
    private final String idiomas;
    private final String pais;
    private final String tipo;
    private final String poster;
    private String sinopse;


    public ModeloSeriePessoal(BuscaSerie buscaSerie) {
        this.titulo = buscaSerie.titulo();
        this.genero = GenerosEnum.fromString(buscaSerie.genero().split(",")[0].trim());
        this.temporadas = OptionalInt.of(Integer.valueOf(buscaSerie.temporadas())).orElse(0);
        this.nota = OptionalDouble.of(Double.valueOf(buscaSerie.nota())).orElse(0);
        this.ano = buscaSerie.ano();
        this.sinopse = buscaSerie.sinopse();
        this.idiomas = buscaSerie.idiomas();
        this.pais = buscaSerie.pais();
        this.tipo = buscaSerie.tipo();
        this.poster = buscaSerie.poster();
    }


    public String toString() {
        return """
                
                = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                
                Nome da Série: %s
                Gênero: %s
                Total de Temporadas: %d
                Avaliação: %.1f
                Ano: %s
                
                Sinopse: %s
                
                Idioma: %s
                Pais: %s
                Tipo: %s
                Poster: %s
                
                """.formatted(getTitulo(), getGenero(), getTemporadas(), getNota(), getAno(),
                getSinopse(), getIdiomas(), getPais(), getTipo(), getPoster());
    }

    public String getTitulo() {
        return titulo;
    }

    public GenerosEnum getGenero() {
        return genero;
    }

    public Integer getTemporadas() {
        return temporadas;
    }

    public Double getNota() {
        return nota;
    }

    public String getAno() {
        return ano;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public String getPais() {
        return pais;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPoster() {
        return poster;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}
