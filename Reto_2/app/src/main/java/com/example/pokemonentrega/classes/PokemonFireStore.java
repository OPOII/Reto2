package com.example.pokemonentrega.classes;

import java.io.Serializable;

public class PokemonFireStore implements Serializable {
    private String Ataque;
    private String Defensa;
    private String Nombre;
    private String Tipo;
    private String UrlFoto;
    private String Velocidad;
    private String Vida;

    public PokemonFireStore(String Ataque, String Defensa, String Nombre, String Tipo, String Urlfoto, String Velocidad, String Vida) {
        this.Ataque = Ataque;
        this.Defensa = Defensa;
        this.Nombre = Nombre;
        this.Tipo = Tipo;
        this.UrlFoto = UrlFoto;
        this.Velocidad = Velocidad;
        this.Vida = Vida;
    }

    public PokemonFireStore() {
    }



    public String getAtaque() {
        return Ataque;
    }

    public void setAtaque(String ataque) {
        Ataque = ataque;
    }

    public String getDefensa() {
        return Defensa;
    }

    public void setDefensa(String defensa) {
        Defensa = defensa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getUrlFoto() {
        return UrlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        UrlFoto = urlFoto;
    }

    public String getVelocidad() {
        return Velocidad;
    }

    public void setVelocidad(String velocidad) {
        Velocidad = velocidad;
    }

    public String getVida() {
        return Vida;
    }

    public void setVida(String vida) {
        Vida = vida;
    }
}
