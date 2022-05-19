package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InfoTrabajador extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth usr = FirebaseAuth.getInstance();

    int califica;
    String trabID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_trabajador);

        TextView nombreInfo = findViewById(R.id.nombreInfo);
        TextView calificiacionInfo = findViewById(R.id.calificacionInfo);
        ImageView fotoInfo = findViewById(R.id.fotoTrabajadorInfo);

        trabID = getIntent().getStringExtra("trabajador");

        // Texview Set
        nombreInfo.setText(getIntent().getStringExtra("nombre"));




        calificiacionInfo.setText(String.valueOf(getIntent().getDoubleExtra("calificacion", 0)));
        Glide.with(this).load(getIntent().getIntExtra
                ("fotoResource", 0)).into(fotoInfo);



        ///// PRUEBAAAa

        calificarTrabajador();
    }


    public void llamarTrabajador(View view){
        String posted_by = "3315879701";

        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    void calificarTrabajador(){
        String usuario = usr.getCurrentUser().getEmail().toString();

        String ruta = "users/" + trabID + "/calificaciones";


        Map<String, Object> calificacion = new HashMap<>();
        calificacion.put("usuario", usuario);
        calificacion.put("calificaion", califica);



        db.collection(ruta).document(usuario).set(calificacion).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(InfoTrabajador.this, "Calififcacion enviada", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InfoTrabajador.this, "No se pudo mandar la calificacion", Toast.LENGTH_SHORT).show();
            }
        });

    }
}