package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAgregarEjercicio;
    LinearLayout layoutEjercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAgregarEjercicio = findViewById(R.id.btnAgregarEjercicio);
        layoutEjercicios = findViewById(R.id.layoutEjercicios);

        btnAgregarEjercicio.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,
                    SeleccionEjerciciosActivity.class);

            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){

            ArrayList<String> lista =
                    data.getStringArrayListExtra("ejercicios");

            layoutEjercicios.removeAllViews();

            for(String ejercicio : lista){

                View view = LayoutInflater.from(this)
                        .inflate(R.layout.item_ejercicio, null);

                TextView txtEjercicio =
                        view.findViewById(R.id.txtEjercicio);

                TextView txtDetalle =
                        view.findViewById(R.id.txtDetalle);

                txtEjercicio.setText(ejercicio);
                txtDetalle.setText("3 series x 15 repeticiones");

                layoutEjercicios.addView(view);
            }
        }
    }


}