package edu.reis.apiTreino.model;

public enum GenerosEnum {

    ACAO("Action"),
    AVENTURA("Adventure"),
    FICCAO_CIENTIFICA("Fantasy"),
    COMEDIA("Comedy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String respostaApi;

    GenerosEnum(String respostaApi) {
        this.respostaApi = respostaApi;
    }

    public static GenerosEnum fromString(String text) {
        for (GenerosEnum genero : GenerosEnum.values()) {
            if (genero.respostaApi.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Não há Categoria Cadastrada Para: " + text);
    }
}
