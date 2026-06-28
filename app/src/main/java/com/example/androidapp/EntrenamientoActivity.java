package com.example.androidapp;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EntrenamientoActivity extends AppCompatActivity {

    ArrayList<Ejercicio> rutina;
    int indiceActual = 0;
    TextView txtNombreEjercicio;
    TextView txtSerie;
    TextView txtTiempo;
    TextView txtSiguiente;
    ImageView imgEjercicio;
    ImageButton btnSiguiente;
    ImageButton btnPausa;
    CountDownTimer timer;
    boolean pausado = false;
    long tiempoRestante;

    int serieActual = 1;
    int segundosPorSerie = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

        rutina = (ArrayList<Ejercicio>) getIntent()
                .getSerializableExtra("rutina");

        if (rutina == null) {
            rutina = new ArrayList<>();
        }

        imgEjercicio = findViewById(R.id.imgEjercicio);

        txtNombreEjercicio = findViewById(R.id.txtNombreEjercicio);
        txtSerie = findViewById(R.id.txtSerie);
        txtTiempo = findViewById(R.id.txtTiempo);
        txtSiguiente = findViewById(R.id.txtSiguiente);

        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnPausa = findViewById(R.id.btnPausa);

        mostrarEjercicio();

        btnSiguiente.setOnClickListener(v -> siguienteEjercicio());

        btnPausa.setOnClickListener(v -> {

            if(pausado){

                iniciarTimer(tiempoRestante);
                btnPausa.setImageResource(android.R.drawable.ic_media_pause);
                pausado = false;

            }else{

                if(timer!=null)
                    timer.cancel();

                btnPausa.setImageResource(android.R.drawable.ic_media_play);
                pausado = true;
            }

        });
    }

    private void mostrarEjercicio() {

        if (rutina.isEmpty()) {

            txtNombreEjercicio.setText("No hay ejercicios");
            txtSerie.setText("");
            txtTiempo.setText("00:00");
            txtSiguiente.setText("");

            btnSiguiente.setEnabled(false);
            btnPausa.setEnabled(false);

            return;
        }
        if (indiceActual >= rutina.size()) {
            txtNombreEjercicio.setText("¡Rutina finalizada!");
            txtSiguiente.setText("");
            txtTiempo.setText("");
            btnSiguiente.setEnabled(false);
            return;
        }

        Ejercicio actual = rutina.get(indiceActual);
        serieActual = 1;
        pausado = false;
        btnPausa.setImageResource(android.R.drawable.ic_media_pause);

        txtNombreEjercicio.setText(actual.getNombre());

        txtSerie.setText(
                "Serie " +
                        serieActual +
                        " de " +
                        actual.getSeries() +
                        " • " + actual.getRepeticiones() + " repeticiones");

        actualizarImagen(actual.getNombre());

        actualizarSiguiente();

        iniciarTimer(segundosPorSerie * 1000);
    }

    private void actualizarSiguiente() {

        if (indiceActual + 1 < rutina.size()) {
            Ejercicio siguiente = rutina.get(indiceActual + 1);
            txtSiguiente.setText("Siguiente: " + siguiente.getNombre());
        } else {
            txtSiguiente.setText("Último ejercicio");
        }
    }

    private void iniciarTimer(long tiempo){

        if(timer!=null)
            timer.cancel();

        timer = new CountDownTimer(tiempo,1000){

            @Override
            public void onTick(long millisUntilFinished){

                tiempoRestante = millisUntilFinished;

                long segundos = millisUntilFinished/1000;

                long minutos = segundos/60;

                segundos = segundos%60;

                txtTiempo.setText(
                        String.format(Locale.getDefault(),
                                "%02d:%02d",
                                minutos,
                                segundos)
                );
            }

            @Override
            public void onFinish(){

                avanzarSerie();
            }

        }.start();

    }

    private void avanzarSerie(){

        Ejercicio actual = rutina.get(indiceActual);

        if(serieActual < actual.getSeries()){

            serieActual++;

            txtSerie.setText(
                    "Serie " +
                            serieActual +
                            " de " +
                            actual.getSeries() +
                            " • " + actual.getRepeticiones() + " repeticiones");

            iniciarTimer(segundosPorSerie * 1000);

        }else{

            indiceActual++;

            mostrarEjercicio();
        }

    }

    private void siguienteEjercicio() {

        if (timer != null) {
            timer.cancel();
        }

        indiceActual++;
        mostrarEjercicio();
    }

    private void actualizarImagen(String nombre){

        switch(nombre){

            case "Plancha":
                imgEjercicio.setImageResource(R.drawable.plancha);
                break;

            case "Sentadillas":
                imgEjercicio.setImageResource(R.drawable.sentadillas);
                break;

            case "Flexiones":
                imgEjercicio.setImageResource(R.drawable.flexiones);
                break;

            case "Zancadas":
                imgEjercicio.setImageResource(R.drawable.zancadas);
                break;

            case "Abdominales":
                imgEjercicio.setImageResource(R.drawable.abdominales);
                break;
        }

    }

}
