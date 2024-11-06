package edu.reis.apiTreino.model;

import edu.reis.apiTreino.principal.Principal;

public class ModeloEpisodioPessoal {

    private final String TITULO_EPISODIO;
    private final String NOME_SERIE;
    private final String NUMERO_EPISODIO;
    private final Integer TEMPORADA;
    private final String DATA_LANCAMENTO;
    private float nota;


    public ModeloEpisodioPessoal(String temporada, String nomeSerie, ModeloEpisodio modeloEpisodio) {
        this.TITULO_EPISODIO = modeloEpisodio.titulo();
        this.NOME_SERIE = nomeSerie;
        this.NUMERO_EPISODIO = modeloEpisodio.episodio();
        this.TEMPORADA = Integer.parseInt(temporada);

        try {
            this.nota = Float.parseFloat(modeloEpisodio.nota());
        } catch (NumberFormatException e) {
            this.nota = 0.0f;
        }

        this.DATA_LANCAMENTO = modeloEpisodio.data();
    }

    public String getTITULO_EPISODIO() {
        return TITULO_EPISODIO;
    }

    public String getNOME_SERIE() {
        return NOME_SERIE;
    }

    public String getNUMERO_EPISODIO() {
        return NUMERO_EPISODIO;
    }

    public Integer getTEMPORADA() {
        return TEMPORADA;
    }

    public String getDATA_LANCAMENTO() {
        return DATA_LANCAMENTO;
    }

    public float getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return """
                
                = = = = = = = = = = = = = = = =
                
                Nome da Série: %s
                Nome do Episodio: %s
                Número do Episodio nº: %s
                Temporada: %d
                Nota: %.1f
                Data Lançamento: %s
                
                """.formatted(getNOME_SERIE(), getTITULO_EPISODIO(), getNUMERO_EPISODIO(), getTEMPORADA(), getNota(), getDATA_LANCAMENTO());
    }
}
