package com.example.pokemonentrega.classes;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonentrega.R;

public class pokeViewModel extends RecyclerView.ViewHolder {
    private Button botonPokemon;
    private ImageView imagen;
    private TextView pokemonName;

    public pokeViewModel(@NonNull View itemView) {
        super(itemView);
        imagen=itemView.findViewById(R.id.pokeImagen);
        botonPokemon=itemView.findViewById(R.id.pokeAction);
        pokemonName=itemView.findViewById(R.id.pokeNombre);
    }

    public Button getPokeAction() {
        return botonPokemon;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public TextView getPokemonName() {
        return pokemonName;
    }
}
