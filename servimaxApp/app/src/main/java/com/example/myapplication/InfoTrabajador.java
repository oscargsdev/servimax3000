package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class InfoTrabajador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_trabajador);

        TextView nombreInfo = findViewById(R.id.nombreInfo);
        TextView calificiacionInfo = findViewById(R.id.calificacionInfo);
        ImageView fotoInfo = findViewById(R.id.fotoTrabajadorInfo);

        nombreInfo.setText(getIntent().getStringExtra("nombre"));
        calificiacionInfo.setText(String.valueOf(getIntent().getDoubleExtra("calificacion", 0)));
        Glide.with(this).load(getIntent().getIntExtra
                ("fotoResource", 0)).into(fotoInfo);
    }


    public void llamarTrabajador(View view){
        String posted_by = "3315879701";

        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}