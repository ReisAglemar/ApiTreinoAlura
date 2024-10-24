package edu.reis.apiTreino.service;

import com.google.gson.Gson;

public class ConverteJsonClasse implements IconverteJsonClasse {

    Gson gson = new Gson();

    @Override
    public <T> T converteTipos(String json, Class<T> classe) {

        return gson.fromJson(json, classe);
    }
}
