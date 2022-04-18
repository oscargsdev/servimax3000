package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TrabajadorAdapter extends RecyclerView.Adapter<TrabajadorAdapter.ViewHolder> {

    private ArrayList<Trabajador> mTrabajadorData;
    private Context mContext;

    // Constructor que pasa datos y contexto
    TrabajadorAdapter(Context context, ArrayList<Trabajador> trabajadorData){
        this.mContext = context;
        this.mTrabajadorData = trabajadorData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.tarjeta_trabajador, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trabajador currentTrabajador = mTrabajadorData.get(position);
        holder.bindTo(currentTrabajador);
    }

    @Override
    public int getItemCount() {
        return mTrabajadorData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Views de la tarjeta
        private ImageView mFotoTrabajador;
        private TextView mNombreTrabajador;
        private TextView mCalificacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mFotoTrabajador = itemView.findViewById(R.id.fotoTrabajador);
            mNombreTrabajador = itemView.findViewById(R.id.nombreTrabajador);
            mCalificacion = itemView.findViewById(R.id.calificacion);
        }

        void bindTo(Trabajador currentTrabajador){
            mNombreTrabajador.setText(currentTrabajador.getNombre());
            mCalificacion.setText(String.valueOf(currentTrabajador.getCalificacion()));
            Glide.with(mContext).load(currentTrabajador.getFotoResource()).into(mFotoTrabajador);
        }
    }
}
