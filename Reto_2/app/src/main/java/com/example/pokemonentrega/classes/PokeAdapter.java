package com.example.pokemonentrega.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonentrega.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.http.Url;

public class PokeAdapter extends RecyclerView.Adapter<pokeViewModel> {

    private ArrayList<PokemonFireStore> lista;
    private OnPokemonClickListener listener;

    public PokeAdapter() {
        lista = new ArrayList<>();
    }

    public void addPokemon(PokemonFireStore poke) {
        lista.add(poke);
        notifyDataSetChanged();
    }


    public void clear() {
        lista.clear();
        notifyDataSetChanged();
    }

    @Override
    public pokeViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.arrow, parent, false);
        pokeViewModel pokeViewModel = new pokeViewModel(view);
        return pokeViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull pokeViewModel holder, int position) {
        PokemonFireStore pokemon=lista.get(position);
        holder.getPokemonName().setText(pokemon.getNombre().substring(0,1).toUpperCase()+pokemon.getNombre().substring(1));
        Glide.with(holder.getImagen()).load(pokemon.getUrlFoto()).into(holder.getImagen());

        holder.getPokeAction().setOnClickListener(v->{
            PokemonFireStore pp=lista.get(position);
            listener.onClickListener(pp);
        });

    }

    public void setListener(OnPokemonClickListener ls) {
      this.listener=ls;
    }

    public interface OnPokemonClickListener {
        void onClickListener(PokemonFireStore pokmon);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
