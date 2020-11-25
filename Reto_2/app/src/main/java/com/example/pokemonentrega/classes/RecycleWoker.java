package com.example.pokemonentrega.classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pokemonentrega.activities.Lobby;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecycleWoker extends Thread {
    private Lobby lobby;
    private FirebaseFirestore db;
    private boolean isAlive;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public RecycleWoker(Lobby lob) {
        lobby = lob;
        isAlive = true;
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void run() {
        ArrayList<PokemonFireStore> pokemons = new ArrayList<>();
        while (isAlive) {

            delay(5000);
            db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PokemonFireStore n = document.toObject(PokemonFireStore.class);
                            pokemons.add(n);
                        }
                    } else {
                        Log.d("SSSS", "Error getting documents: ", task.getException());
                    }
                }
            });
           // lobby.mandarInfo(pokemons);
            pokemons.clear();
        }
    }

    public void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
