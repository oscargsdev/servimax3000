package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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


        trabajadoresRef = db.collection("users");
        query = trabajadoresRef.whereEqualTo("oficio", getIntent().getStringExtra("oficio").toLowerCase(Locale.ROOT));
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    TypedArray fotoImageResources = getResources().obtainTypedArray(R.array.dummy_fotos);

                    for (QueryDocumentSnapshot document: task.getResult()){
//                        Trabajador trabajador = document.toObject(Trabajador.class);
//                        mTrabajadorData.add(trabajador);



                        mTrabajadorData.add(new Trabajador(document.get("nombre").toString(), document.get("apellido").toString(),
                                document.getString("telefono"),  document.getString("oficio"), document.getString("minimo"),
                                document.getString("maximo") , fotoImageResources.getResourceId(0, 0), document.getId()));
                    }
                    mAdapter.notifyDataSetChanged();


                }
                else{
                    Toast.makeText(ListaTrabajadores.this, "Fallo lol", Toast.LENGTH_LONG).show();
                }
            }
        });


//        initializeData();
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

        return super.onOptionsItemSelected(item);
    }

    public void cerrarSesion(){
//        checkUser();
        mAuth.signOut();
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

}