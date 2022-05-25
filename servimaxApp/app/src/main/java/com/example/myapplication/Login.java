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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Login extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usrRef;
    private Query query;

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

//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            Toast.makeText(this, "No usuario", Toast.LENGTH_SHORT).show();
//        }

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
//        Toast.makeText(Login.this, user.getEmail().toString(),
//                Toast.LENGTH_SHORT).show();




        DocumentReference docRef = db.collection("users").document(user.getEmail().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("d", "DocumentSnapshot data: " + document.getData());
                        String usr = document.getString("tipoUsr");
                        Intent i;

//                        Toast.makeText(Login.this, usr,
//                                Toast.LENGTH_SHORT).show();

                        if(usr.equals("1")){

                            i = new Intent(Login.this, HomeTrabajador.class);
                            i.putExtra("trabajador", document.getId().toString());
                        }
                        else {
                            i = new Intent(Login.this, ListaOficios.class);
                        }

                        startActivity(i);
                    } else {
                        Log.d("d", "No such document");
                    }
                } else {
                    Log.d("d", "get failed with ", task.getException());
                }
            }
        });






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