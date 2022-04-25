package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
}