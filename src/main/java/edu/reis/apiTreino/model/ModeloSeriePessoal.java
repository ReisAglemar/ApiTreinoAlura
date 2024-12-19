package edu.reis.apiTreino.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

@Entity
@Table(name = "series")
public class ModeloSeriePessoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private final String titulo;

    @Enumerated(EnumType.STRING)
    private final GenerosEnum genero;

    @Column(name = "qtd_temporadas")
    private final Integer temporadas;

    private final Double nota;

    @Column(name = "ano_lancamento")
    private final String ano;

    private final String idiomas;
    private final String pais;
    private final String tipo;
    private final String poster;
    private String sinopse;

    @OneToMany(mappedBy = "modeloSeriePessoal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ModeloEpisodioPessoal> episodios = new ArrayList<>();


    public ModeloSeriePessoal() {
        this.titulo = null;
        this.genero = null;
        this.temporadas = null;
        this.nota = null;
        this.ano = null;
        this.idiomas = null;
        this.pais = null;
        this.tipo = null;
        this.poster = null;
    }

    public ModeloSeriePessoal(BuscaSerie buscaSerie) {
        this.titulo = buscaSerie.titulo();
        this.genero = GenerosEnum.fromApi(buscaSerie.genero().split(",")[0].trim());
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
                
                Episodios: %s
                
                """.formatted(getTitulo(), getGenero(), getTemporadas(), getNota(), getAno(),
                getSinopse(), getIdiomas(), getPais(), getTipo(), getPoster(), this.episodios);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<ModeloEpisodioPessoal> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<ModeloEpisodioPessoal> episodios) {
        episodios.forEach(e -> e.setModeloSeriePessoal(this));
        this.episodios = episodios;
    }
}
