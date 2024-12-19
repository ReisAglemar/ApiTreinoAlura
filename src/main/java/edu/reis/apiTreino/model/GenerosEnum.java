package edu.reis.apiTreino.model;

public enum GenerosEnum {

    ACAO("Action", "ação"),
    AVENTURA("Adventure", "aventura"),
    FICCAO_CIENTIFICA("Fantasy", "ficção"),
    COMEDIA("Comedy", "comédia"),
    ROMANCE("Romance", "romance"),
    DRAMA("Drama", "drama"),
    CRIME("Crime", "crime");

    private String respostaApi;
    private String buscaEmPortugues;

    GenerosEnum(String respostaApi, String buscaEmPortugues) {
        this.respostaApi = respostaApi;
        this.buscaEmPortugues = buscaEmPortugues;
    }

    public static GenerosEnum fromApi(String text) {
        for (GenerosEnum genero : GenerosEnum.values()) {
            if (genero.respostaApi.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Não há Categoria Cadastrada Para: " + text);
    }

    public static GenerosEnum fromBuscaEmPortugues(String text) {
        for (GenerosEnum genero : GenerosEnum.values()) {
            if (genero.buscaEmPortugues.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Não há Categoria Cadastrada Para: " + text);
    }
}
