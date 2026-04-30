package com.example.androidapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionEjerciciosActivity extends AppCompatActivity {


    Button btnAgregar, btnCancelar;

    CheckBox chk1, chk2, chk3, chk4, chk5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ejercicios);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnAgregar.setOnClickListener(v -> {

            Toast.makeText(this,
                    "Rutina agregada",
                    Toast.LENGTH_SHORT).show();
            finish();
        });

        btnCancelar.setOnClickListener(v -> {
            finish();
        });
    }
}
