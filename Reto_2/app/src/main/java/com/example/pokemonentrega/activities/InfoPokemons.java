package com.example.pokemonentrega.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokemonentrega.R;
import com.example.pokemonentrega.classes.PokemonFireStore;

public class InfoPokemons extends AppCompatActivity {
    private PokemonFireStore mypoke;
    private TextView nombre;
    private TextView defensa;
    private TextView ataque;
    private TextView velocidad;
    private TextView vida;
    private TextView defensaPS;
    private TextView ataquePS;
    private TextView velodicadPS;
    private TextView vidaPS;
    private Button liberar;
    private ImageView imagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pokemons);
        mypoke= (PokemonFireStore)getIntent().getExtras().getSerializable("mypoke");
        nombre= findViewById(R.id.nameType);
        defensa= findViewById(R.id.defensa);
        defensaPS= findViewById(R.id.defensaPS);
        ataque= findViewById(R.id.ataque);
        ataquePS= findViewById(R.id.ataquePS);
        velocidad= findViewById(R.id.velocidad);
        velodicadPS= findViewById(R.id.velocidadPS);
        vida= findViewById(R.id.vida);
        vidaPS= findViewById(R.id.vidaPS);
        liberar=findViewById(R.id.liberarButton);
        imagen=findViewById(R.id.imagenPokemon);

        nombre.setText(mypoke.getNombre().substring(0,1).toUpperCase()+mypoke.getNombre().substring(1)+System.getProperty("line.separator")+"("+mypoke.getTipo().substring(0,1).toUpperCase()+mypoke.getTipo().substring(1)+")");

        defensa.setText("Defensa");
        defensaPS.setText(mypoke.getDefensa());

        ataque.setText("Ataque");
        ataquePS.setText(mypoke.getAtaque());

        velocidad.setText("Velocidad");
        velodicadPS.setText(mypoke.getVelocidad());

        vida.setText("Vida");
        vidaPS.setText(mypoke.getVida());

        Glide.with(imagen).load(mypoke.getUrlFoto()).into(imagen);
    }
}