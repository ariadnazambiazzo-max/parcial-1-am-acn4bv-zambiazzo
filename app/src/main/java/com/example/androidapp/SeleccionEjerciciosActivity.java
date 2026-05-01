package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SeleccionEjerciciosActivity extends AppCompatActivity {


    Button btnAgregar, btnCancelar;

    CheckBox chk1, chk2, chk3, chk4, chk5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ejercicios);

        chk1 = findViewById(R.id.chk1);
        chk2 = findViewById(R.id.chk2);
        chk3 = findViewById(R.id.chk3);
        chk4 = findViewById(R.id.chk4);
        chk5 = findViewById(R.id.chk5);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnAgregar.setOnClickListener(v -> {
            ArrayList<String> lista = new ArrayList<>();

            if(chk1.isChecked()) lista.add("Sentadillas");
            if(chk2.isChecked()) lista.add("Plancha");
            if(chk3.isChecked()) lista.add("Zancadas");
            if(chk4.isChecked()) lista.add("Flexiones");
            if(chk5.isChecked()) lista.add("Abdominales");

            Intent intent = new Intent();
            intent.putStringArrayListExtra("ejercicios", lista);

            setResult(RESULT_OK, intent);
            finish();
        });

        btnCancelar.setOnClickListener(v -> {
            finish();
        });
    }
}
