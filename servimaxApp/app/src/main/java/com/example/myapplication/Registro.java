package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    EditText emailET;
    EditText passET;

    RadioButton rbUsr;
    RadioButton rbTrab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this, "No usuario", Toast.LENGTH_SHORT).show();
        }

        emailET = findViewById(R.id.email_registro_text);
        passET = findViewById(R.id.password_registro_text);

        rbUsr = findViewById(R.id.rbUsuario);
        rbTrab = findViewById(R.id.rbTrab);
    }

    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("D", "createUserWithEmail:success");
                            Toast.makeText(Registro.this, "Usuario creado :)",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("D", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registro.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user){

        if (rbUsr.isChecked()) {
            Intent intent = new Intent(this, RegistroUsuario.class);
            intent.putExtra("email", user.getEmail().toString());
            intent.putExtra("tipo", "0");
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, RegistroTrabajador.class);
            intent.putExtra("email", user.getEmail().toString());
            intent.putExtra("tipo", "1");
            startActivity(intent);
        }
    }

    public void registro(View view) {
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();

        createAccount(email, pass);
    }

    public void loginView(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }
}