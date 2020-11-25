package com.example.pokemonentrega.classes;

public class Form {
    private String url;
    private String name;

    public Form(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Form() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
