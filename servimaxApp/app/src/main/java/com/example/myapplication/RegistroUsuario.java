package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroUsuario extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    EditText nombreUsr;
    EditText apellidosUsr;
    EditText telefonoUsr;

    boolean datosValidos = true;

    Validacion v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        nombreUsr = findViewById(R.id.nombre_usr_text);
        apellidosUsr = findViewById(R.id.apellido_usr_text);
        telefonoUsr = findViewById(R.id.telefono_usr_text);

        v = new Validacion();


        // Pa despistar
        Toast.makeText(RegistroUsuario.this, "Ingrese un no. de telefono valido", Toast.LENGTH_LONG).show();
    }

    public void completarRegUsr(View view) {
        String nombre = nombreUsr.getText().toString();
        String apellido = apellidosUsr.getText().toString();
        String telefono = telefonoUsr.getText().toString();

        boolean vNombre = v.valNombre(nombre);
        boolean vApellido = v.valApellido(apellido);
        boolean vTel = v.valTelefono(telefono);

        String email = getIntent().getStringExtra("email");
        String tipoUsr = getIntent().getStringExtra("tipo");

        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre);
        user.put("apellido", apellido);
        user.put("telefono", telefono);
        user.put("tipoUsr", tipoUsr);

        if (vNombre && vApellido && vTel){
            db.collection("users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(RegistroUsuario.this, "Registro completo :)", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistroUsuario.this, "No se pudo completar el registro :(", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            if (!vNombre){
                Toast.makeText(RegistroUsuario.this, "Ingrese un nombre valido", Toast.LENGTH_SHORT).show();
            }
            if (!vApellido){
                Toast.makeText(RegistroUsuario.this, "Ingrese un apellido valido", Toast.LENGTH_SHORT).show();
            }
            if(!vTel){
                Toast.makeText(RegistroUsuario.this, "Ingrese un no. de telefono valido", Toast.LENGTH_SHORT).show();
            }
        }






    }
}