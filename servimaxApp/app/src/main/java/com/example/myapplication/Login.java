package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    EditText emailET;
    EditText passET;

    Validacion v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        v = new Validacion();

        // Initialize Firebase Auth
//        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this, "No usuario", Toast.LENGTH_SHORT).show();
        }

        emailET = findViewById(R.id.email_login_text);
        passET = findViewById(R.id.password_login_text);
    }

    private void signInAccount(String email, String password){
        boolean vEmail = v.valCorreo(email);
        boolean vPass = v.valPass(password);

        if (vEmail && vPass){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("D", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("D", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
        else{
            if (!vEmail){
                Toast.makeText(Login.this, "Ingrese un email valido",
                        Toast.LENGTH_SHORT).show();
            }

            if (!vPass){
                Toast.makeText(Login.this, "Ingrese una contraseña valida",
                        Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void updateUI(FirebaseUser user){
        Toast.makeText(Login.this, user.getEmail().toString(),
                Toast.LENGTH_SHORT).show();

        Intent i = new Intent(Login.this, ListaOficios.class);
        startActivity(i);
    }

    public void registroView(View view) {
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }



    public void login(View view) {
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();

        signInAccount(email, pass);
    }

    public void cerrarSesion(){
        mAuth.signOut();
    }
}