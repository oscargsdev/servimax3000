package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaOficios extends AppCompatActivity {

    private ArrayList<String> listOficios;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_oficios);

        recycler = (RecyclerView) findViewById(R.id.recyclerViewTrabajador);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        listOficios = new ArrayList<>();

        initializeData();

    }

    private void initializeData(){
        String[] nombresList = getResources().getStringArray(R.array.dummy_oficios);

        listOficios.clear();

        listOficios.addAll(Arrays.asList(nombresList));
        OficioAdapter adapter = new OficioAdapter(listOficios);
        recycler.setAdapter(adapter);
    }
}