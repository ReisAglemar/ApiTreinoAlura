package edu.reis.apiTreino.model;

public class ModeloEpisodioPessoal {

    private final String TITULO;
    private final String NUMERO_EPISODIO;
    private final Integer TEMPORADA;
    private final String DATA_LANCAMENTO;
    private float nota;


    public ModeloEpisodioPessoal(String temporada, ModeloEpisodio modeloEpisodio) {
        this.TITULO = modeloEpisodio.titulo();
        this.NUMERO_EPISODIO = modeloEpisodio.episodio();
        this.TEMPORADA = Integer.parseInt(temporada);

        try {
            this.nota = Float.parseFloat(modeloEpisodio.nota());
        } catch (NumberFormatException e) {
            this.nota = 0.0f;
        }

        this.DATA_LANCAMENTO = modeloEpisodio.data();
    }

    public String getTITULO() {
        return TITULO;
    }


    public String getNUMERO_EPISODIO() {
        return NUMERO_EPISODIO;
    }

    public int getTEMPORADA() {
        return TEMPORADA;
    }

    public float getNota() {
        return nota;
    }

    public String getDATA_LANCAMENTO() {
        return DATA_LANCAMENTO;
    }

    @Override
    public String toString() {
        return """
                
                ======== Resultado Para os Melhores Episódios ========
                
                Nome Episodio: %s
                Número do Episodio nº: %s
                Temporada: %d
                Nota: %.1f
                Data Lançamento: %s
                
                """.formatted(getTITULO(), getNUMERO_EPISODIO(), getTEMPORADA(), getNota(), getDATA_LANCAMENTO());
    }
}
