package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private ArrayList<Message> mChatData;
    private Context mContext;


    // Constructor que pasa datos y contexto
    ChatAdapter(Context context, ArrayList<Message> chatData){
        this.mContext = context;
        this.mChatData = chatData;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.tarjeta_mensaje, parent, false));

    }



    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Message currentChat = mChatData.get(position);
        holder.bindTo(currentChat);
    }

    @Override
    public int getItemCount() {
        return mChatData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView mUsr;
        private TextView mMsg;
        private TextView mTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            mUsr = itemView.findViewById(R.id.nombreMsg);
            mMsg = itemView.findViewById(R.id.cuerpoMsg);
            mTime = itemView.findViewById(R.id.mTime);
        }


        public void bindTo(Message currentChat) {

            mUsr.setText(currentChat.getSender());
            mMsg.setText(currentChat.getMessage());
            mTime.setText(currentChat.getDate());
        }
    }




}
