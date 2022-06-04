package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistroTrabajador extends AppCompatActivity  {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    EditText nombreTra;
    EditText apellidosTra;
    EditText telefonoTra;
    EditText oficioTra;
    EditText minTra;
    EditText maxTra;

    Spinner spinnerOficio;
    String oficioM;

    Validacion v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_trabajador);

        createNotificationChannel();

        nombreTra = findViewById(R.id.nombre_trab_text);
        apellidosTra = findViewById(R.id.apellido_trab_text);
        telefonoTra = findViewById(R.id.telefono_trab_text);
//        oficioTra = findViewById(R.id.oficio_trab_text);
        minTra = findViewById(R.id.precio_min_text);
        maxTra = findViewById(R.id.precio_max_text);

        spinnerOficio = (Spinner) findViewById(R.id.spinnerOficio);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dummy_oficios, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerOficio.setAdapter(adapter);




//        spinnerOficio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                oficioM = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(RegistroTrabajador.this, oficioM, Toast.LENGTH_SHORT).show();
//            }
//        });


        v = new Validacion();


    }



    public void completarRegTra(View view) {
        String nombre = nombreTra.getText().toString();
        String apellido = apellidosTra.getText().toString();
        String telefono = telefonoTra.getText().toString();
        String oficio = spinnerOficio.getSelectedItem().toString().toLowerCase(Locale.ROOT);
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
                    muestraNotifiacion("Registro", "Usted se ha registrado como trabajador. Bienvenido.");
                    startActivity(new Intent(RegistroTrabajador.this, HomeTrabajador.class));
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


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID="Servimax3000";
            CharSequence name = "Mensaje de Servimax3000";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setShowBadge(true);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void muestraNotifiacion(String cabecera, String cuerpo){
        int icon = R.drawable.servimax56;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder
                (getApplicationContext(),"Servimax3000")
                .setAutoCancel(true)
                .setContentTitle(cabecera)
                .setContentText(cuerpo)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource
                        (getResources(), R.drawable.servimax56))
                .setTicker(cuerpo)
                .setAutoCancel(false)
                .build();
        manager.notify(1,notification);
    }


}