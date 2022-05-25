package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Perfil extends AppCompatActivity {

    TextView nombre;
    TextView email;
    TextView calificacion;
    ImageView estrella;
    ImageView fotoPerfil;

    StorageReference storageReference;



    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth usr = FirebaseAuth.getInstance();
    private  FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);



        nombre = findViewById(R.id.nombrePerfil);
        email = findViewById(R.id.emailPerfil);
        fotoPerfil = findViewById(R.id.fotoPerfil);

        calificacion = findViewById(R.id.calificacionPerfil);

        estrella = findViewById(R.id.estrellaPerfil);


        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fotoPerfilRef = storageReference.child(auth.getCurrentUser().getEmail().toString() + ".jpg");
        fotoPerfilRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(fotoPerfil);
            }
        });



        db.collection("users").document(usr.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();

                nombre.setText(doc.getString("nombre") + " " + doc.getString("apellido"));

                String tipo = doc.getString("tipoUsr");
                if (tipo.equals("1")){
                    calificacion.setText(String.valueOf(doc.getDouble("calificacion")));



                }
                else{
                    calificacion.setVisibility(View.GONE);
                    estrella.setVisibility(View.GONE);
                }

            }
        });

        email.setText(usr.getCurrentUser().getEmail());

        db.collection("users/" + usr.getCurrentUser().getEmail()
                + "/calificaciones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int cont = 0;
                    double cal = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        cal += document.getDouble("calificacion");
                        cont++;

                    }
                    calificacion.setText(String.valueOf(cal / cont));

//                    cal = cal / cont;

                } else {



                }


            }




        });

    }


    public void restablecerContraPerfil(View view) {
        startActivity(new Intent(Perfil.this, RestablecerContra.class));

    }

    public void cerrarSesionPerfil(View view) {
        auth.signOut();
        startActivity(new Intent(Perfil.this, Login.class));
    }

    public void cambiarFoto(View v){
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galeria, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == RESULT_OK){
                Uri imageUri = data.getData();
//                fotoPerfil.setImageURI(imageUri);
                subirImagen(imageUri);
            }
        }
    }

    private void subirImagen(Uri imagenUri){
        StorageReference fileRef = storageReference.child(auth.getCurrentUser().getEmail().toString() + ".jpg");
        fileRef.putFile(imagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Perfil.this, "Foto actualizada correctamente", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(fotoPerfil);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Perfil.this, "No se pudo actualizar la foto", Toast.LENGTH_SHORT).show();
            }
        });

    }
}