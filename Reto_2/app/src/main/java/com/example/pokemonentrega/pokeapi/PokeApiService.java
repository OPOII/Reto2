package com.example.pokemonentrega.pokeapi;

import com.example.pokemonentrega.classes.Pokemon;
import com.example.pokemonentrega.classes.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApiService {
    @GET
    Call<PokemonRespuesta> obtenerListaDeRetorno();
}
