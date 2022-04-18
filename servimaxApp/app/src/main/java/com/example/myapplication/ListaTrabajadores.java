package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;

public class ListaTrabajadores extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Trabajador> mTrabajadorData;
    private TrabajadorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trabajadores);

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
}