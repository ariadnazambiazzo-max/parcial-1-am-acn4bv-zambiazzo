package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SeleccionEjerciciosActivity extends AppCompatActivity {

    Spinner spEjercicios;
    EditText txtSeries, txtRepeticiones;
    Button btnAgregar, btnGuardarRutina, btnCancelar;

    ListView listaRutina;


    ArrayList<Ejercicio> rutina = new ArrayList<>();

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ejercicios);

        rutina = (ArrayList<Ejercicio>) getIntent()
                .getSerializableExtra("rutina");

        if (rutina == null) {
            rutina = new ArrayList<>();
        }

        spEjercicios = findViewById(R.id.spEjercicios);
        txtSeries = findViewById(R.id.txtSeries);
        txtRepeticiones = findViewById(R.id.txtRepeticiones);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnGuardarRutina = findViewById(R.id.btnGuardarRutina);
        btnCancelar = findViewById(R.id.btnCancelar);

        listaRutina = findViewById(R.id.listaRutina);

        String[] ejercicios = {
                "Sentadillas",
                "Plancha",
                "Flexiones",
                "Zancadas",
                "Abdominales"
        };

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        ejercicios);

        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );

        listaRutina.setAdapter(adapter);

        spEjercicios.setAdapter(spinnerAdapter);

        actualizarLista();

        btnAgregar.setOnClickListener(v -> agregarEjercicio());

        btnGuardarRutina.setOnClickListener(v -> guardarRutina());

        btnCancelar.setOnClickListener(v -> finish());

    }


    private void agregarEjercicio(){

        String nombre = spEjercicios.getSelectedItem().toString();

        if(txtSeries.getText().toString().isEmpty()
                || txtRepeticiones.getText().toString().isEmpty()){

            Toast.makeText(this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int series = Integer.parseInt(txtSeries.getText().toString());
        int repeticiones = Integer.parseInt(txtRepeticiones.getText().toString());

        Ejercicio ejercicio =
                new Ejercicio(nombre, series, repeticiones);

        rutina.add(ejercicio);

        actualizarLista();

        txtSeries.setText("");
        txtRepeticiones.setText("");

    }

    private void guardarRutina() {

        Intent intent = new Intent();
        intent.putExtra("rutina", rutina);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void actualizarLista() {

        if (adapter == null || rutina == null) return;

        ArrayList<String> textos = new ArrayList<>();

        for (Ejercicio e : rutina) {
            textos.add(
                    e.getNombre() + " - " +
                            e.getSeries() + " x " +
                            e.getRepeticiones()
            );
        }

        adapter.clear();
        adapter.addAll(textos);
        adapter.notifyDataSetChanged();
    }
}


