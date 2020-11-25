package com.example.pokemonentrega.classes;

public class Stat {
    private String name;
    private String url;

    public Stat(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public Stat(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
