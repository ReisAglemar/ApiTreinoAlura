package edu.reis.apiTreino.service;

public interface IconverteJsonClasse {

    <T> T converteTipos(String json, Class<T> classe);
}
