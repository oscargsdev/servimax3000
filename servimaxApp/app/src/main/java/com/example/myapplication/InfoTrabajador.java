package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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





    EditText opinionET;
    TextView nombreInfo;
    TextView calificiacionInfo;
    ImageView fotoInfo;
    TextView oficioInfo;
    TextView teleInfo;
    TextView precioInfo;
    Button opinionBtn;




    // Vars firebase
    int califica = 0;
    String ruta;
    String usuario;
    String opn;

    Button borrarCali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_trabajador);

        nombreInfo = findViewById(R.id.nombreInfo);
        calificiacionInfo = findViewById(R.id.calificacionInfo);
        fotoInfo = findViewById(R.id.fotoTrabajadorInfo);
        oficioInfo = findViewById(R.id.oficioInfo);
        teleInfo = findViewById(R.id.telefonoInfo);
        precioInfo = findViewById(R.id.precioInfo);
        opinionET = findViewById(R.id.etOpinion);
        opinionBtn = findViewById(R.id.btnOpinion);

        opinionET.setVisibility(View.INVISIBLE);
        opinionBtn.setVisibility(View.INVISIBLE);


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


        // Pa firebase
        trabID = getIntent().getStringExtra("trabajador");




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
                btnEstrellaAccion();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 2;
               btnEstrellaAccion();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 3;
                btnEstrellaAccion();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 4;
                btnEstrellaAccion();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                califica = 5;
                btnEstrellaAccion();
            }
        });







        ///// PRUEBAAAa

//        calificarTrabajador();
    }


    void btnEstrellaAccion(){
        calificarTrabajador();
        borrarCali.setVisibility(View.VISIBLE);
        opinionET.setVisibility(View.VISIBLE);
        opinionBtn.setVisibility(View.VISIBLE);

        if (califica == 1){
            Toast.makeText(this, "Ha calificado a este trabajador con "
                    + califica + " estrella", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Ha calificado a este trabajador con "
                    + califica + " estrellas", Toast.LENGTH_SHORT).show();
        }

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
        opinionET.setVisibility(View.INVISIBLE);
        opinionBtn.setVisibility(View.INVISIBLE);
    }

    public void enviaOpinion(View v){
        usuario = usr.getCurrentUser().getEmail().toString();

        ruta = "users/" + trabID + "/calificaciones";

        String opn = opinionET.getText().toString();


        Map<String, Object> calificacion = new HashMap<>();
        calificacion.put("usuario", usuario);
        calificacion.put("calificacion", califica);
        calificacion.put("opinion", opn);



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