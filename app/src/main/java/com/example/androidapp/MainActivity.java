package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAgregarEjercicio;
    Button btnComenzar;
    LinearLayout layoutEjercicios;
    ArrayList<Ejercicio> rutinaActual = new ArrayList<>();

    ActivityResultLauncher<Intent> launcher;

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
        btnComenzar = findViewById(R.id.btnComenzar);
        layoutEjercicios = findViewById(R.id.layoutEjercicios);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        ArrayList<Ejercicio> data =
                                (ArrayList<Ejercicio>) result.getData()
                                        .getSerializableExtra("rutina");

                        if (data != null) {
                            rutinaActual = data;
                        }

                        layoutEjercicios.removeAllViews();

                        for (Ejercicio e : rutinaActual) {
                            agregarVista(e);
                        }
                    }
                });

        btnAgregarEjercicio.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,
                    SeleccionEjerciciosActivity.class);

            intent.putExtra("rutina", rutinaActual);

            launcher.launch(intent);
        });

        btnComenzar.setOnClickListener(v -> {

            if (rutinaActual.isEmpty()) {
                Toast.makeText(this,
                        "No hay ejercicios en la rutina",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, EntrenamientoActivity.class);
            intent.putExtra("rutina", rutinaActual);
            startActivity(intent);
        });
    }

    private void agregarVista(Ejercicio ejercicio){

        View view = LayoutInflater.from(this)
                .inflate(R.layout.item_ejercicio, layoutEjercicios, false);

        TextView txtEjercicio = view.findViewById(R.id.txtEjercicio);
        TextView txtDetalle = view.findViewById(R.id.txtDetalle);
        Button btnEliminar = view.findViewById(R.id.btnEliminar);

        txtEjercicio.setText(ejercicio.getNombre());

        txtDetalle.setText(
                ejercicio.getSeries() +
                        " series x " +
                        ejercicio.getRepeticiones() +
                        " repeticiones"
        );

        btnEliminar.setOnClickListener(v -> {
            layoutEjercicios.removeView(view);
            rutinaActual.remove(ejercicio);
        });

        layoutEjercicios.addView(view);
    }

}