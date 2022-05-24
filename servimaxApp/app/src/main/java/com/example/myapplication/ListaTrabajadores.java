package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ListaTrabajadores extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Trabajador> mTrabajadorData;
    private TrabajadorAdapter mAdapter;

    private TextView title;

    private FirebaseAnalytics mFirebaseAnalytics;
    private static FirebaseAuth mAuth;

    private FirebaseFirestore db;
    private CollectionReference trabajadoresRef;
    private Query query;

    private Query qCalif;

    StorageReference storageReference;

    ArrayList<Double> calfs = new ArrayList<Double>();

    private double calificacion = 0;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trabajadores);

        title = findViewById(R.id.oficioTitle);
        title.setText(getIntent().getStringExtra("oficio"));

        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recyclerViewTrabajador);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mTrabajadorData = new ArrayList<>();

        mAdapter = new TrabajadorAdapter(this, mTrabajadorData);
        mRecyclerView.setAdapter(mAdapter);


        db = FirebaseFirestore.getInstance();


        // Storage reference
        storageReference = FirebaseStorage.getInstance().getReference();



        trabajadoresRef = db.collection("users");
        query = trabajadoresRef.whereEqualTo("oficio", getIntent().getStringExtra("oficio").toLowerCase(Locale.ROOT));
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    TypedArray fotoImageResources = getResources().obtainTypedArray(R.array.dummy_fotos);

                    storageReference = FirebaseStorage.getInstance().getReference();

                    for (QueryDocumentSnapshot document: task.getResult()){
//                        Trabajador trabajador = document.toObject(Trabajador.class);
//                        mTrabajadorData.add(trabajador);

//                        double calif = queryCalificacionUsr(document.getId());

//                        Toast.makeText(ListaTrabajadores.this, String.valueOf(calif), Toast.LENGTH_SHORT).show();

                        queryCalificacionUsr(document.getId());

                        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                "://" + getResources().getResourcePackageName(R.drawable.ppfblank)
                                + '/' + getResources().getResourceTypeName(R.drawable.ppfblank) + '/' + getResources().getResourceEntryName(R.drawable.ppfblank) );


                        mTrabajadorData.add(new Trabajador(document.get("nombre").toString(), document.get("apellido").toString(),
                                document.getString("telefono"),  document.getString("oficio"),
                                5.0, document.getString("minimo"),
                                document.getString("maximo") , imageUri, document.getId()));


//                        StorageReference fotoPerfilRef = storageReference.child(document.getId().toString() + ".jpg");
//                        fotoPerfilRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                storageReference = FirebaseStorage.getInstance().getReference();
//                                StorageReference fotoPerfilRef = storageReference.child("ppfBlank.jpg");
//                                fotoPerfilRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        mTrabajadorData.add(new Trabajador(document.get("nombre").toString(), document.get("apellido").toString(),
//                                                document.getString("telefono"),  document.getString("oficio"),
//                                                5.0, document.getString("minimo"),
//                                                document.getString("maximo") , uri, document.getId()));
//                                    }
//                                });
//                            }
//                        });





                    }
                    mAdapter.notifyDataSetChanged();


                    loadFoto();

                }
                else{
                    Toast.makeText(ListaTrabajadores.this, "Fallo lol", Toast.LENGTH_LONG).show();
                }
            }
        });


//        initializeData();
    }


    private void loadFoto(){


        for (int i = 0; i < mTrabajadorData.size(); i++){
            StorageReference fotoPerfilRef = storageReference.child(mTrabajadorData.get(i).getEmail() + ".jpg");
            int finalI = i;
            fotoPerfilRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                mTrabajadorData.get(finalI).setFotoResource(uri);
                                mAdapter.notifyDataSetChanged();
                            }
                        });

        }
    }

//    private void initializeData(){
//        String[] nombresList = getResources().getStringArray(R.array.dummy_nombres);
//        String[] calificacionesList = getResources().getStringArray(R.array.dummy_calificaciones);
//        TypedArray fotoImageResources = getResources().obtainTypedArray(R.array.dummy_fotos);
//
//        mTrabajadorData.clear();
//
//        for (int i = 0; i < nombresList.length; i++){
//            mTrabajadorData.add(new Trabajador(nombresList[i], Double.parseDouble(calificacionesList[i]),
//                    fotoImageResources.getResourceId(i, 0)));
//        }
//    }


    // VIEJO CON DATOS ESTATICOS
//    private void initializeData(){
//        String[] nombresList = getResources().getStringArray(R.array.dummy_nombres);
//        String[] calificacionesList = getResources().getStringArray(R.array.dummy_calificaciones);
//        TypedArray fotoImageResources = getResources().obtainTypedArray(R.array.dummy_fotos);
//
//        mTrabajadorData.clear();
//
//        for (int i = 0; i < nombresList.length; i++){
//            mTrabajadorData.add(new Trabajador(nombresList[i], Double.parseDouble(calificacionesList[i]),
//                    fotoImageResources.getResourceId(i, 0)));
//        }
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrarSesion) {
            cerrarSesion();
            return true;
        }
        if(id == R.id.perfil){
            startActivity(new Intent(ListaTrabajadores.this, Perfil.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void cerrarSesion(){
//        checkUser();
        mAuth.signOut();
        startActivity(new Intent(ListaTrabajadores.this, Login.class));
//        checkUser();
    }

    void checkUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(getApplicationContext(), "No usuario", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), currentUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
        }
    }



    double queryCalificacionUsr(String usr){



        final int[] cont = {0};
        final double[] cal = {0};



        db.collection("users/" + usr + "/calificaciones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        calfs.add(document.getDouble("calificacion"));
//                        cont[0]++;
//                        Toast.makeText(ListaTrabajadores.this,
//                                "usuario: " + document.getString("usuario")
//                                        + " Cal: " + document.getDouble("calificacion"), Toast.LENGTH_SHORT).show();
                    }


//                    Toast.makeText(ListaTrabajadores.this,
//                            " Cal dentro: " + promedio, Toast.LENGTH_SHORT).show();




                } else {

                    Toast.makeText(ListaTrabajadores.this, "Fallo en calif lol", Toast.LENGTH_SHORT).show();

                }


            }



        });

//        Toast.makeText(ListaTrabajadores.this,
//                " Cal fuera: " + calificacion, Toast.LENGTH_SHORT).show();
//

//        return cal/cont;
        double calProm = 0;
        for (int i = 0; i < calfs.size(); i++){
            calProm += calfs.get(i);
        }

        calProm = calProm / calfs.size();
        return calProm;


    }

//
//    double queryCalificacionUsr(String usr){
//
//        final String[] cont = {""};
//        final String[] cal = {""};
//
//        db.collection("users/" + usr + "/calificaciones")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                cal[0] += document.getString("opinion");
////                                cont[0]++;
//                            }
//                        } else {
//
//                            Toast.makeText(ListaTrabajadores.this, "Fallo en calif lol", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//
//                });
//
//        System.out.println(cal[0]);
//        System.out.println(cont[0]);
//
////        if (cont[0] == 0){
////            return 0;
////        }
////
////        return cal[0] / cont[0];
//
//        return 0;
//
//
//    }

}