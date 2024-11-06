package edu.reis.apiTreino.model;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class ModeloSeriePessoal {

    private String titulo;
    private String genero;
    private Integer temporadas;
    private Double nota;
    private String ano;
    private String sinopse;
    private String idiomas;
    private String pais;
    private String tipo;
    private String poster;


    public ModeloSeriePessoal(BuscaSerie buscaSerie) {
        this.titulo = buscaSerie.titulo();
        this.genero = buscaSerie.genero();
        this.temporadas = OptionalInt.of(Integer.valueOf(buscaSerie.temporadas())).orElse(0);
        this.nota = OptionalDouble.of(Double.valueOf(buscaSerie.nota())).orElse(0);
        this.ano = buscaSerie.ano();
        this.sinopse = buscaSerie.sinopse();
        this.idiomas = buscaSerie.idiomas();
        this.pais = buscaSerie.pais();
        this.tipo = buscaSerie.tipo();
        this.poster = buscaSerie.poster();
    }

    @Override
    public String toString() {
        return
                "titulo='" + titulo +
                ", genero='" + genero +
                ", temporadas=" + temporadas +
                ", nota=" + nota +
                ", ano='" + ano +
                ", sinopse='" + sinopse +
                ", idiomas='" + idiomas +
                ", pais='" + pais +
                ", tipo='" + tipo +
                ", poster='" + poster;
    }
}
