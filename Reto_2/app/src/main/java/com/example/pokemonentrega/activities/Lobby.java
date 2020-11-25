package com.example.pokemonentrega.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemonentrega.classes.RecycleWoker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.pokemonentrega.R;
import com.example.pokemonentrega.classes.PokeAdapter;
import com.example.pokemonentrega.classes.Pokemon;
import com.example.pokemonentrega.classes.PokemonFireStore;
import com.example.pokemonentrega.pokeapi.Constants;
import com.example.pokemonentrega.pokeapi.HTTPSWebUtilDomi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.internal.GsonBuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lobby extends AppCompatActivity implements HTTPSWebUtilDomi.OnResponseListener, PokeAdapter.OnPokemonClickListener, InfoPokemons.Listener {
    private RecyclerView lista;
    private PokeAdapter pokeAdapter;
    private Retrofit retrofit;
    private EditText buscarEnApiPokemon;
    private EditText buscarEnStoragePokemon;
    private Button botonEnApi;
    private Button botonEnStorage;
    private HTTPSWebUtilDomi domi;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Pokemon pokemon;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private RecycleWoker worker;
    private boolean mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        lista = findViewById(R.id.pokeLista);
        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        domi = new HTTPSWebUtilDomi();
        domi.setListener(this);

        pokeAdapter = new PokeAdapter();
        pokeAdapter.setListener(this);
        lista.setAdapter(pokeAdapter);
        lista.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lista.setLayoutManager(manager);

        //Buscar en el API
        botonEnApi = findViewById(R.id.botonAtrapar);
        buscarEnApiPokemon = findViewById(R.id.pokemonName);

        //Buscar en Firebase

        botonEnStorage = findViewById(R.id.botonBuscarPokemon);
        buscarEnStoragePokemon = findViewById(R.id.buscarPokemon);

        //worker=new RecycleWoker(this);
        // worker.start();
        this.runOnUiThread(()->{
            ArrayList<PokemonFireStore> n = new ArrayList<>();
            Task<QuerySnapshot> dis = db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados").get().addOnCompleteListener(task -> {
                Log.e("MOSTRARNOS EL HPTA ID>>>>>>>>>>>>><<<<<:)","EEEEEEEEEEEEEEEEEEEEEEEEEEOOOOOOOOOOOOOOOOOO");
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    PokemonFireStore nuevo = doc.toObject(PokemonFireStore.class);
                    n.add(nuevo);

                    pokeAdapter.addPokemon(nuevo);
                }
            });
            //////////////////////
        });



        botonEnApi.setOnClickListener(
                (v) -> {
                    String nombrePokemon = buscarEnApiPokemon.getText().toString().toLowerCase();

                    new Thread(() -> {
                        domi.GETrequest(Constants.SEARCH_CALLBACK, "https://pokeapi.co/api/v2/pokemon/" + nombrePokemon);
                    }).start();

                }
        );
        botonEnStorage.setOnClickListener(
                (v)->{
                    String nombrePokemonBuscar=buscarEnStoragePokemon.getText().toString().toLowerCase();
                    new Thread(()->{
                        Log.d("SSSS", "Entro en el hiloooo");
                       db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados")
                               .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                           @Override
                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                               if(task.isSuccessful()){
                                   Log.d("SSSS", "El task Se completo bien >>>>>>>>>>>>>>>>>>>>>>><<");
                                   for (QueryDocumentSnapshot document: task.getResult()){

                                       if(nombrePokemonBuscar.equals(document.get("Nombre"))){
                                           PokemonFireStore pk=document.toObject(PokemonFireStore.class);
                                           pokeAdapter.clear();
                                           pokeAdapter.addPokemon(pk);
                                           Log.d("SSSS", "Entro donde necesitaba que entrara <<<<<<<<<<<<<<<<<");
                                       }
                                   }
                               }
                           }
                       });
                    }).start();
                }
        );

    }


    @Override
    public void onResponse(int callbackID, String response) {
        switch (callbackID) {
            case Constants.SEARCH_CALLBACK:

                this.runOnUiThread(() -> {
                    Gson gson = new Gson();
                    pokemon = gson.fromJson(response, Pokemon.class);
                    Map<String, Object> data = new HashMap<>();
                    data.put("Nombre", pokemon.getForms()[0].getName());
                    data.put("Defensa", pokemon.getStats()[3].getBase_stat() + "");
                    data.put("Ataque", pokemon.getStats()[1].getBase_stat() + "");
                    data.put("Velocidad", pokemon.getStats()[5].getBase_stat() + "");
                    data.put("Vida", pokemon.getStats()[0].getBase_stat() + "");
                    data.put("UrlFoto", pokemon.getSprites().getFront_default());
                    data.put("Tipo", pokemon.getTypes()[0].getType().getName());
                    if (auth.getCurrentUser() != null) {
                        db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Lobby.this, "Se atrapo el pokemon: " + pokemon.getForms()[0].getName() + "!! Bien hecho", Toast.LENGTH_LONG);
                                refrescarVista();
                            }
                        });
                    }

                });

                break;
                case Constants.CATCH_CALLBACK:
                    this.runOnUiThread(()->{

                    });
                    break;

        }
    }


    @Override
    public void onClickListener(PokemonFireStore pokmon) {
    Intent intent=new Intent(this,InfoPokemons.class);
    intent.putExtra("mypoke",  pokmon);
    startActivity(intent);
    }
    public void refrescarVista(){
        pokeAdapter = new PokeAdapter();
        pokeAdapter.setListener(Lobby.this);
        lista.setAdapter(pokeAdapter);
        lista.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Lobby.this);
        ArrayList<PokemonFireStore> n = new ArrayList<>();
        Task<QuerySnapshot> dis = db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados").get().addOnCompleteListener(task -> {
            Log.e("MOSTRARNOS EL HPTA ID>>>>>>>>>>>>><<<<<:)","EEEEEEEEEEEEEEEEEEEEEEEEEEOOOOOOOOOOOOOOOOOO");
            for (QueryDocumentSnapshot doc : task.getResult()) {
                PokemonFireStore nuevo = doc.toObject(PokemonFireStore.class);
                n.add(nuevo);

                pokeAdapter.addPokemon(nuevo);
            }
        });
    }

    @Override
    public void actualizar() {
        refrescarVista();
    }
}