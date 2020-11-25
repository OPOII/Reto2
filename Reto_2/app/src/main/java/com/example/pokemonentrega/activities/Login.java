package com.example.pokemonentrega.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemonentrega.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button botonIngresar;
    private Button botonRegistrar;
    private EditText usuario;
    private EditText contrasena;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth= FirebaseAuth.getInstance();
        botonIngresar=findViewById(R.id.BotonIngresar);
        botonRegistrar=findViewById(R.id.BotonRegister);
        usuario=findViewById(R.id.TrainerID);
        contrasena=findViewById(R.id.TrainerKey);

        botonIngresar.setOnClickListener(
                (v)->{
                    String user= usuario.getText().toString().trim();
                    String contra=contrasena.getText().toString().trim();
                    if(TextUtils.isEmpty(user)){
                        usuario.setText("Por favor ingrese su usuario");
                    }
                    if(contra.length()<6){
                        contrasena.setText("La contraseña debe de ser de al menos 6 digitos");
                        return;
                    }
                    if(TextUtils.isEmpty(contra)){
                        contrasena.setText("Por favor ingrese una contraseña");
                    }
                    auth.signInWithEmailAndPassword(user, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                            startActivity(new Intent(Login.this,Lobby.class));
                            }else{
                                Toast.makeText(Login.this,"No se pudo loggear",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        );
        botonRegistrar.setOnClickListener(
                (v)->{
startActivity(new Intent(getApplicationContext(),Register.class));
                }
        );
    };

}