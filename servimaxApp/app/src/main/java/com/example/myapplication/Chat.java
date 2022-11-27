package com.example.myapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;
import static java.text.DateFormat.getDateTimeInstance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.text.DateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Chat extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth usr = FirebaseAuth.getInstance();

    private FirebaseAnalytics mFirebaseAnalytics;
    private static FirebaseAuth mAuth;


    private Query chatRef;
    private Query query;

    private Context mContext;


    String trabID; // email
    String userID;

    TextView trab;
    TextView user;
    TextView title;

    Button enviar;
    EditText mensaje;

    RecyclerView recyclerView;
    private ArrayList<Message> mChatData;
    private ChatAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Timestamp instant= Timestamp.now();

        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        // Recycler
        recyclerView = findViewById(R.id.recyclerChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mChatData = new ArrayList<>();

//        trab = findViewById(R.id.trabPrueba);
//        user = findViewById(R.id.usrPrueba);

        title = findViewById(R.id.chatTitle);

        trabID = getIntent().getStringExtra("trabajador");
        userID = getIntent().getStringExtra("user");
//        trab.setText(trabID);
//        user.setText(userID);

        mensaje = findViewById(R.id.mensaje);

        enviar = findViewById(R.id.bEnviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensaje(mensaje.getText().toString(), userID, "hoy");
            }
        });



        Map<String, Object> chat = new HashMap<>();
        chat.put("user", userID);
        chat.put("trab", trabID);


        db.collection("chats").document(userID+trabID).set(chat, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Chat.this, "chat creado completo :)", Toast.LENGTH_SHORT).show();
//                muestraNotifiacion("Registro", "Usted se ha registrado como usuario. Bienvenido.");
//                startActivity(new Intent(RegistroUsuario.this, ListaOficios.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Chat.this, "No se pudo completar el registro :(", Toast.LENGTH_SHORT).show();
            }
        });


        DocumentReference docRef = db.collection("users").document(trabID);
        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.CACHE;

        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    Log.d("d", "Cached document data: " + document.getData());

                    title.setText(document.getString("nombre") + " " + document.getString("apellido"));
                } else {
                    Log.d("d", "Cached get failed: ", task.getException());
                }
            }
        });



//        enviarMensaje("hola, " + trabID, userID, "hoy");
//        enviarMensaje("es bueno tenerte de vuelta", userID, "hoy");
//        enviarMensaje("te parece buena esta app?", userID, "hoy");

        chatRef = db.collection("chats/" + userID + trabID + "/messages").orderBy("tstamp");
        chatRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("D", "Listen failed.", error);
                    return;
                }

                mChatData.clear();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("message") != null) {
                        Message m = new Message(doc.getString("sender"), doc.getString("message"), String.valueOf(doc.getTimestamp("tstamp").toDate()));
//                        mChatData.clear();
                        mChatData.add(m);
                        Log.w("W", mChatData.toString());
                    }
                }

                mAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
            }


        });






        mAdapter = new ChatAdapter(this, mChatData);
        recyclerView.setAdapter(mAdapter);




    }

    private void enviarMensaje(String m, String s, String d){
        Map<String, Object> msg = new HashMap<>();
        msg.put("message", m);
        msg.put("sender", s);
        msg.put("tstamp", Timestamp.now());


        db.collection("chats/" + userID + trabID + "/messages").add(msg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(Chat.this, "mensaje enviado completo :)", Toast.LENGTH_SHORT).show();
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Chat.this, "No se pudo enviar el mensaje", Toast.LENGTH_SHORT).show();
            }
        });

        mensaje.setText("");

    }


    public static String getTimeDate(long timestamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }
}