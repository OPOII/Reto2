package com.example.pokemonentrega.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.pokemonentrega.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText nombre;
    private EditText contra;
    private Button agregar;
    private EditText email;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nombre=findViewById(R.id.nombreInicio);
        contra=findViewById(R.id.contrasena);
        agregar=findViewById(R.id.button);
        email=findViewById(R.id.emailTrainer);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        Map<String,Object> note=new HashMap<>();
        note.put("email",email.getText());
        note.put("name",nombre.getText());
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        agregar.setOnClickListener(
                (v)->{
                    auth.createUserWithEmailAndPassword(nombre.getText().toString(),contra.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                startActivity(new Intent(getApplicationContext(),Lobby.class));
                            }


                        }
                    });
                    auth.signInWithEmailAndPassword(nombre.getText().toString(),contra.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                db.collection("users").document(auth.getCurrentUser().getUid()).set(note);
                                startActivity(new Intent(getApplicationContext(),Lobby.class));
                            }
                        }
                    });

                }

        );

    }
}