package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroTrabajador extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    EditText nombreTra;
    EditText apellidosTra;
    EditText telefonoTra;
    EditText oficioTra;
    EditText minTra;
    EditText maxTra;

    Validacion v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_trabajador);

        nombreTra = findViewById(R.id.nombre_trab_text);
        apellidosTra = findViewById(R.id.apellido_trab_text);
        telefonoTra = findViewById(R.id.telefono_trab_text);
        oficioTra = findViewById(R.id.oficio_trab_text);
        minTra = findViewById(R.id.precio_min_text);
        maxTra = findViewById(R.id.precio_max_text);

        v = new Validacion();

        Toast.makeText(RegistroTrabajador.this, "Ingrese un oficio valido", Toast.LENGTH_SHORT).show();
    }



    public void completarRegTra(View view) {
        String nombre = nombreTra.getText().toString();
        String apellido = apellidosTra.getText().toString();
        String telefono = telefonoTra.getText().toString();
        String oficio = oficioTra.getText().toString();
        String minimo = minTra.getText().toString();
        String maximo = maxTra.getText().toString();

        boolean vNombre = v.valNombre(nombre);
        boolean vApellido = v.valApellido(apellido);
        boolean vTel = v.valTelefono(telefono);
        boolean vOficio = v.valOficio(oficio);
        boolean vMin = v.valMin(minimo);
        boolean vMax = v.valMax(maximo);

        String email = getIntent().getStringExtra("email");
        String tipoUsr = getIntent().getStringExtra("tipo");

        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre);
        user.put("apellido", apellido);
        user.put("telefono", telefono);
        user.put("oficio", oficio);
        user.put("minimo", minimo);
        user.put("maximo", maximo);
        user.put("tipoUsr", tipoUsr);


        if (vNombre && vApellido && vTel && vOficio && vMin && vMax){
            db.collection("users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(RegistroTrabajador.this, "Registro completo :)", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistroTrabajador.this, "No se pudo completar el registro :(", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{

            if (!vNombre){
                Toast.makeText(RegistroTrabajador.this, "Ingrese un nombre valido", Toast.LENGTH_SHORT).show();
            }
            if (!vApellido){
                Toast.makeText(RegistroTrabajador.this, "Ingrese un apellido valido", Toast.LENGTH_SHORT).show();
            }
            if(!vTel){
                Toast.makeText(RegistroTrabajador.this, "Ingrese un no. de telefono valido", Toast.LENGTH_SHORT).show();
            }
            if (!vOficio){
                Toast.makeText(RegistroTrabajador.this, "Ingrese un oficio valido", Toast.LENGTH_SHORT).show();
            }
            if(!vMin){
                Toast.makeText(RegistroTrabajador.this, "Ingrese un monto minimo valido", Toast.LENGTH_SHORT).show();
            }
            if (!vMax){
                Toast.makeText(RegistroTrabajador.this, "Ingrese un monto maximo valido", Toast.LENGTH_SHORT).show();
            }


        }


    }
}