package com.example.androidapp;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EntrenamientoActivity extends AppCompatActivity {

    ArrayList<Ejercicio> rutina;
    int indiceActual = 0;

    TextView txtEjercicioActual;
    TextView txtSiguiente;
    TextView txtTimer;

    Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

        rutina = (ArrayList<Ejercicio>) getIntent()
                .getSerializableExtra("rutina");

        if (rutina == null) {
            rutina = new ArrayList<>();
        }

        txtEjercicioActual = findViewById(R.id.txtEjercicioActual);
        txtSiguiente = findViewById(R.id.txtSiguiente);
        txtTimer = findViewById(R.id.txtTimer);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        mostrarEjercicio();

        btnSiguiente.setOnClickListener(v -> siguienteEjercicio());
    }

    private void mostrarEjercicio() {
        if (indiceActual >= rutina.size()) {
            txtEjercicioActual.setText("¡Rutina finalizada!");
            txtSiguiente.setText("");
            btnSiguiente.setEnabled(false);
            return;
        }

        Ejercicio actual = rutina.get(indiceActual);

        txtEjercicioActual.setText(
                actual.getNombre() + "\n" +
                        actual.getSeries() + " x " +
                        actual.getRepeticiones()
        );

        if (indiceActual + 1 < rutina.size()) {
            Ejercicio siguiente = rutina.get(indiceActual + 1);

            txtSiguiente.setText("Siguiente: " + siguiente.getNombre());
        } else {
            txtSiguiente.setText("Último ejercicio");
        }

        iniciarTimer();
    }

    CountDownTimer timer;

    private void iniciarTimer() {

        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(30000, 1000) { // 30 segundos

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Tiempo: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                txtTimer.setText("¡Listo!");
            }

        }.start();
    }

    private void siguienteEjercicio() {

        indiceActual++;

        mostrarEjercicio();
    }

}
