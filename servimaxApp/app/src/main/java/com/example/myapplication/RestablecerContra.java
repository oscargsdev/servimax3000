package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerContra extends AppCompatActivity {

    private EditText correo;
    private Button btnReset;
    TextView textRegresar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contra);

        correo = (EditText) findViewById(R.id.correo_res_text);
        btnReset = (Button) findViewById(R.id.btn_reset);
        textRegresar = (TextView) findViewById(R.id.textRegresar);



        auth = FirebaseAuth.getInstance();

        correo.setText(auth.getCurrentUser().getEmail().toString());

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = correo.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(),
                            "Escriba el coreo electrónico que tiene resgistrado",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RestablecerContra.this,"Te hemos enviado instrucciones" +
                                                " para que reestablecer tu contraseña",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        textRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}