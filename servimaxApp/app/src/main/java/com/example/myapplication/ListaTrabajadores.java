package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListaTrabajadores extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Trabajador> mTrabajadorData;
    private TrabajadorAdapter mAdapter;

    private FirebaseAnalytics mFirebaseAnalytics;
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trabajadores);

        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recyclerViewTrabajador);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTrabajadorData = new ArrayList<>();

        mAdapter = new TrabajadorAdapter(this, mTrabajadorData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();
    }

    private void initializeData(){
        String[] nombresList = getResources().getStringArray(R.array.dummy_nombres);
        String[] calificacionesList = getResources().getStringArray(R.array.dummy_calificaciones);
        TypedArray fotoImageResources = getResources().obtainTypedArray(R.array.dummy_fotos);

        mTrabajadorData.clear();

        for (int i = 0; i < nombresList.length; i++){
            mTrabajadorData.add(new Trabajador(nombresList[i], Double.parseDouble(calificacionesList[i]),
                    fotoImageResources.getResourceId(i, 0)));
        }
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