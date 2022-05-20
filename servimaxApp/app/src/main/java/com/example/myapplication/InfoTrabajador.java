package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    // Info trabjador
    String trabID; // email
    String nombreC;
    String min;
    String max;
    String noTelefono;
    String oficio;




    // Vars firebase
    int califica = 0;
    String ruta;
    String usuario;

    Button borrarCali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_trabajador);

        TextView nombreInfo = findViewById(R.id.nombreInfo);
        TextView calificiacionInfo = findViewById(R.id.calificacionInfo);
        ImageView fotoInfo = findViewById(R.id.fotoTrabajadorInfo);
        TextView oficioInfo = findViewById(R.id.oficioInfo);
        TextView teleInfo = findViewById(R.id.telefonoInfo);
        TextView precioInfo = findViewById(R.id.precioInfo);


        nombreC = getIntent().getStringExtra("nombre") + " " + getIntent().getStringExtra("apellido");
        oficio = getIntent().getStringExtra("oficio");
        oficio = oficio.substring(0, 1).toUpperCase() + oficio.substring(1);
        min = getIntent().getStringExtra("min");
        max = getIntent().getStringExtra("max");
        noTelefono = getIntent().getStringExtra("noTel");



        // Set TextViews
        nombreInfo.setText(nombreC);
        oficioInfo.setText(oficio);
        teleInfo.setText(noTelefono);
        precioInfo.setText("$" + min + " - $" + max + " /hr");
        oficioInfo.setText(oficio);



        calificiacionInfo.setText(String.valueOf(getIntent().getDoubleExtra("calificacion", 0)));
        Glide.with(this).load(getIntent().getIntExtra
                ("fotoResource", 0)).into(fotoInfo);




        /// BOTONES

        Button b1 = findViewById(R.id.estrella1);
        Button b2 = findViewById(R.id.estrella2);
        Button b3 = findViewById(R.id.estrella3);
        Button b4 = findViewById(R.id.estrella4);
        Button b5 = findViewById(R.id.estrella5);

        borrarCali = findViewById(R.id.borrarCali);
        borrarCali.setVisibility(View.INVISIBLE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 1;
                calificarTrabajador();
                borrarCali.setVisibility(View.VISIBLE);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 2;
                calificarTrabajador();
                borrarCali.setVisibility(View.VISIBLE);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 3;
                calificarTrabajador();
                borrarCali.setVisibility(View.VISIBLE);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 4;
                calificarTrabajador();
                borrarCali.setVisibility(View.VISIBLE);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 5;
                calificarTrabajador();
                borrarCali.setVisibility(View.VISIBLE);
            }
        });

        trabID = getIntent().getStringExtra("trabajador");





        ///// PRUEBAAAa

//        calificarTrabajador();
    }


    public void llamarTrabajador(View view){
        String posted_by = noTelefono;

        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }


    public void borrarCalificacion(View v){
        db.collection(ruta).document(usuario)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InfoTrabajador.this, "Calififcacion borrada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InfoTrabajador.this, "La calificacion no pudo ser borrada", Toast.LENGTH_SHORT).show();
                    }
                });

        borrarCali.setVisibility(View.INVISIBLE);
    }



    void calificarTrabajador(){



        usuario = usr.getCurrentUser().getEmail().toString();

        ruta = "users/" + trabID + "/calificaciones";


        Map<String, Object> calificacion = new HashMap<>();
        calificacion.put("usuario", usuario);
        calificacion.put("calificacion", califica);



        db.collection(ruta).document(usuario).set(calificacion).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(InfoTrabajador.this, "Calififcacion enviada", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InfoTrabajador.this, "No se pudo enviar la calificacion", Toast.LENGTH_SHORT).show();
            }
        });

    }
}