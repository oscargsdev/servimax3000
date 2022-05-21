package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.ViewHolder> {


    private ArrayList<Opinion> mOpinionData;
    private Context mContext;

    // Constructor que pasa datos y contexto
    OpinionAdapter(Context context, ArrayList<Opinion> opinionData){
        this.mContext = context;
        this.mOpinionData = opinionData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.tarjeta_comentario, parent, false));

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Opinion currentOpinion = mOpinionData.get(position);
        holder.bindTo(currentOpinion);
    }

    @Override
    public int getItemCount() {
        return mOpinionData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mCali;
        private TextView mUsr;
        private TextView mComen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCali = itemView.findViewById(R.id.calificacionOpinion);
            mUsr = itemView.findViewById(R.id.nombreUsrOpinion);
            mComen = itemView.findViewById(R.id.comentarioOpinion);
        }


        public void bindTo(Opinion currentOpinion) {
            mCali.setText(currentOpinion.getCalificacion());
            mUsr.setText(currentOpinion.getUsuario());
            mComen.setText(currentOpinion.getComentario());
        }
    }





}
