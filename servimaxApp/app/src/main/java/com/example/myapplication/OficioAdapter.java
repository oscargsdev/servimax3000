package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OficioAdapter extends RecyclerView.Adapter<OficioAdapter.ViewHolderOficios> {

    ArrayList<String> listOficios;
    private Context mContext;

    // Constructor que pasa datos
    public OficioAdapter(Context context, ArrayList<String> listOficios) {
        this.mContext = context;
        this.listOficios = listOficios;
    }

    @NonNull
    @Override
    public OficioAdapter.ViewHolderOficios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.tarjeta_oficio,parent,false);
        return new ViewHolderOficios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OficioAdapter.ViewHolderOficios holder, int position) {
        String currentOficio = listOficios.get(position);
        holder.asignarDatos(listOficios.get(position));
    }

    @Override
    public int getItemCount() {

        return listOficios.size();
    }

    public class ViewHolderOficios extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView btnOficio;

        public ViewHolderOficios(@NonNull View itemView) {
            super(itemView);

            btnOficio = (TextView) itemView.findViewById(R.id.btnOficio);
            itemView.setOnClickListener(this);
        }

        public void asignarDatos(String oficio) {
            btnOficio.setText(oficio);
        }

        @Override
        public void onClick(View view) {

            String currentOficio = listOficios.get(getAdapterPosition());
            Intent intentInfo = new Intent(mContext, ListaTrabajadores.class);
            intentInfo.putExtra("oficio", currentOficio);

            Toast.makeText(mContext.getApplicationContext(), currentOficio, Toast.LENGTH_LONG).show();

            mContext.startActivity(intentInfo);
        }
    }
}
