package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaOficios extends AppCompatActivity {


    private ArrayList<String> listOficios;
    RecyclerView recycler;

    private FirebaseAnalytics mFirebaseAnalytics;
    private  FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_oficios);

        recycler = (RecyclerView) findViewById(R.id.recyclerViewTrabajador);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        listOficios = new ArrayList<>();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initializeData();

    }

    private void initializeData(){
        String[] nombresList = getResources().getStringArray(R.array.dummy_oficios);

        listOficios.clear();

        listOficios.addAll(Arrays.asList(nombresList));
        OficioAdapter adapter = new OficioAdapter(this, listOficios);
        recycler.setAdapter(adapter);
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
            return true;
        }
        if(id == R.id.perfil){
            Intent i = new Intent(ListaOficios.this, Perfil.class);
            i.putExtra("usr", mAuth.getCurrentUser());

            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

     public void cerrarSesion(){
//        checkUser();
        mAuth.signOut();
        startActivity(new Intent(ListaOficios.this, Login.class));
//        checkUser();
    }

//     void checkUser(){
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            Toast.makeText(getApplicationContext(), "No usuario", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(getApplicationContext(), currentUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
//        }
//    }

}