package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OficioAdapter extends RecyclerView.Adapter<OficioAdapter.ViewHolderOficios> {

    ArrayList<String> listOficios;

    // Constructor que pasa datos
    public OficioAdapter(ArrayList<String> listOficios) {
        this.listOficios = listOficios;
    }

    @NonNull
    @Override
    public OficioAdapter.ViewHolderOficios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tarjeta_oficio,parent,false);
        return new ViewHolderOficios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OficioAdapter.ViewHolderOficios holder, int position) {

        holder.asignarDatos(listOficios.get(position));
    }

    @Override
    public int getItemCount() {

        return listOficios.size();
    }

    public class ViewHolderOficios extends RecyclerView.ViewHolder{

        Button btnOficio;

        public ViewHolderOficios(@NonNull View itemView) {
            super(itemView);

            btnOficio = (Button) itemView.findViewById(R.id.btnOficio);
        }

        public void asignarDatos(String oficio) {
            btnOficio.setText(oficio);
        }
    }
}
