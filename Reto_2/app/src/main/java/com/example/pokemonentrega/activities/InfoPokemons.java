package com.example.pokemonentrega.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokemonentrega.R;
import com.example.pokemonentrega.classes.PokemonFireStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private Listener listener;
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
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
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
        liberar.setOnClickListener((v)->{
            String nombreBuscar=mypoke.getNombre().toString().toLowerCase();
            new Thread(()->{
                db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String id = document.getId();
                                        if(nombreBuscar.equals(document.get("Nombre"))){
                                            db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados").document(id).delete();
                                            startActivity(new Intent(getApplicationContext(),Lobby.class));
                                            //listener.actualizar();
                                        }
                                        Log.e("MOSTRARNOS EL HPTA ID>>>>>>>>>>>>><<<<<:)",id);
                                        //or you can store these id in array list
                                    }
                                }
                            }
                        });
            }).start();
        });
    }
    public interface Listener {
        void actualizar();
    }
    public void setListener(Listener listener){
        this.listener=listener;
    }
}