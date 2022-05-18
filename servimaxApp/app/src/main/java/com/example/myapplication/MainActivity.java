package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private FirebaseAnalytics mFirebaseAnalytics;
    private static FirebaseAuth mAuth;

    String[] nombres;
    String[] apellidos;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = getResources().getStringArray(R.array.nombresRegistro);
        apellidos = getResources().getStringArray(R.array.apesRegistro);

        mAuth = FirebaseAuth.getInstance();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle b = new Bundle();
        b.putString("message", "integracion completa");
        mFirebaseAnalytics.logEvent("InitScreep", b);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this, "No usuario", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, currentUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
        }


        //llenadoDeBD();


        // CAMBIAR PARA LA SS
        Intent intent = new Intent(this, ListaOficios.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    static public void cerrarSesion(){
        mAuth.signOut();
        //checkUser();
    }

//    public static void checkUser(){
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            Toast.makeText(getApplicationContext(), "No usuario", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(MainActivity.this, currentUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
//        }
//    }

    //commit del gabo
    //no tengo idea de como funciona esta cosa D:





    void llenadoDeBD(){



        for (int i = 0; i < nombres.length; i++){

            String email = nombres[i] + "@hotmail.com";
            int minR = (int)(Math.random()*(1000-200+1)+200);
            int maxR = (int)(Math.random()*(1000-1500+1)+1500);

            bdLlena(nombres[i], apellidos[i], "3315879701",
                            "electricista", String.valueOf(minR), String.valueOf(maxR),
                            "1",  email, "1234qwer");
        }


    }




    void crearTrabajador(String nombre, String apellido, String telefono,
                         String oficio, String minimo, String maximo,
                         String tipoUsr, String email, String password){


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("D", "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Usuario creado :)",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser usr = mAuth.getCurrentUser();


                            Map<String, Object> user = new HashMap<>();
                            user.put("nombre", nombre);
                            user.put("apellido", apellido);
                            user.put("telefono", telefono);
                            user.put("oficio", oficio);
                            user.put("minimo", minimo);
                            user.put("maximo", maximo);
                            user.put("tipoUsr", tipoUsr);



                            db.collection("users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Registro completo :)", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "No se pudo completar el registro :(", Toast.LENGTH_SHORT).show();
                                }
                            });







                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("D", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }


    void bdLlena(String nombre, String apellido, String telefono,
                 String oficio, String minimo, String maximo,
                 String tipoUsr, String email, String password){
        FirebaseUser usr = mAuth.getCurrentUser();


        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre);
        user.put("apellido", apellido);
        user.put("telefono", telefono);
        user.put("oficio", oficio);
        user.put("minimo", minimo);
        user.put("maximo", maximo);
        user.put("tipoUsr", tipoUsr);



        db.collection("users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Registro completo :)", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "No se pudo completar el registro :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

}