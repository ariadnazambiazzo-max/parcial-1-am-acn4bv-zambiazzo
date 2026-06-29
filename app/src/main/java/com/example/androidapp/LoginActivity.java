package com.example.androidapp;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etContrasenia;
    Button btnLogin;
    TextView tvGoRegistro;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

     //   if (auth.getCurrentUser() != null) {
       //     startActivity(new Intent(this, MainActivity.class));
      //      finish();
      // }

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoRegistro = findViewById(R.id.tvGoRegistro);

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String pass = etContrasenia.getText().toString();

            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        tvGoRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
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
