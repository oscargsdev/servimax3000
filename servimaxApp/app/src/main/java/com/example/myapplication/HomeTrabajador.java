package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeTrabajador extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private RecyclerView mRecyclerView;
    private ArrayList<Opinion> mOpinionData;
    private OpinionAdapter mAdapter;

    TextView nomSaludo;


    private CollectionReference opinionesRef;
    private Query query;
    String trabID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_trabajador);

        mOpinionData = new ArrayList<>();

        nomSaludo = findViewById(R.id.nombreHomeTrab);

//        db.collection("users").document(mAuth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                nomSaludo.setText("Hola, " + documentSnapshot.getString("nombre"));
//            }
//        });
//
//        db.clearPersistence();
//


        mRecyclerView = findViewById(R.id.recyclerOpHomeT);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // FIRESTORE
        // Pa firebase
        trabID = getIntent().getStringExtra("trabajador");


        opinionesRef = db.collection("users/" + trabID + "/calificaciones");
        query = opinionesRef.orderBy("opinion");





        mRecyclerView = findViewById(R.id.recyclerOpHomeT);



        queryOpiniones();

        mAdapter = new OpinionAdapter(this, mOpinionData);
        mRecyclerView.setAdapter(mAdapter);




    }

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
            startActivity(new Intent(HomeTrabajador.this, Login.class));
            return true;
        }
        if(id == R.id.perfil){
            startActivity(new Intent(HomeTrabajador.this, Perfil.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void cerrarSesion(){
//        checkUser();
        mAuth.signOut();
//        checkUser();
    }


    void queryOpiniones(){
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){



                    for (QueryDocumentSnapshot document: task.getResult()){

                        Opinion o = new Opinion(document.getString("usuario"),
                                String.valueOf(document.getDouble("calificacion")).substring(0),
                                document.getString("opinion"));
                        mOpinionData.add(o);




                    }

                    mAdapter.notifyDataSetChanged();


                }
            }
        });
    }
}