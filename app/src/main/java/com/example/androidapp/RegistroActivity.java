package com.example.androidapp;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {

    EditText etEmail, etContrasenia;
    Button btnRegistro;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        auth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String pass = etContrasenia.getText().toString();

            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        TextView logo = findViewById(R.id.logo_FitHome);

        String text = "FitHome";

        SpannableString spannable = new SpannableString(text);

        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#954dcd")),
                3,
                7,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        logo.setText(spannable);
    }



}
