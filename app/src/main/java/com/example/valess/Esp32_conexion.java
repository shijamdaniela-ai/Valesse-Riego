package com.example.valess;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Esp32_conexion extends AppCompatActivity {

    private TextView tvEstadoConexion, tvDetalleDispositivo;
    private Button btnEscanear, btnConectarEsp32;
    private ProgressBar progressBarEsp32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esp32_conexion);

        tvEstadoConexion = findViewById(R.id.tvEstadoConexion);
        tvDetalleDispositivo = findViewById(R.id.tvDetalleDispositivo);
        btnEscanear = findViewById(R.id.btnEscanear);
        btnConectarEsp32 = findViewById(R.id.btnConectarEsp32);
        progressBarEsp32 = findViewById(R.id.progressBarEsp32);

        // Acción al presionar "Escanear ESP32 Cercano"
        btnEscanear.setOnClickListener(v -> simularEscaneo());

        // Acción al presionar "Conectar con Módulo"
        btnConectarEsp32.setOnClickListener(v -> simularConexionExitosa());
    }

    private void simularEscaneo() {
        progressBarEsp32.setVisibility(View.VISIBLE);
        btnEscanear.setEnabled(false);
        tvEstadoConexion.setText("Estado: Buscando dispositivos...");
        tvEstadoConexion.setTextColor(android.graphics.Color.parseColor("#F57C00")); // Naranja
        tvDetalleDispositivo.setText("Buscando módulos ESP32 en el entorno local...");

        // Simulamos un retraso de 2 segundos de escaneo
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            progressBarEsp32.setVisibility(View.GONE);
            btnEscanear.setEnabled(true);

            tvEstadoConexion.setText("Estado: Dispositivo encontrado");
            tvEstadoConexion.setTextColor(android.graphics.Color.parseColor("#388E3C")); // Verde
            tvDetalleDispositivo.setText("Dispositivo detectado: ESP32_Valesse_Riego (RSSI: -45 dBm)");

            btnConectarEsp32.setEnabled(true);
            Toast.makeText(this, "¡ESP32 encontrado con éxito!", Toast.LENGTH_SHORT).show();
        }, 2000);
    }

    private void simularConexionExitosa() {
        progressBarEsp32.setVisibility(View.VISIBLE);
        btnConectarEsp32.setEnabled(false);
        tvEstadoConexion.setText("Estado: Conectando...");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            progressBarEsp32.setVisibility(View.GONE);
            tvEstadoConexion.setText("Estado: ¡Conectado a ESP32!");
            tvEstadoConexion.setTextColor(android.graphics.Color.parseColor("#1B5E20")); // Verde oscuro
            tvDetalleDispositivo.setText("Enlace estable vía Bluetooth/Wi-Fi Local. Sensores de humedad sincronizados.");

            Toast.makeText(this, "Conexión establecida con éxito", Toast.LENGTH_LONG).show();

            // Espera 1.5 segundos y salta a la actividad de plantas
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(Esp32_conexion.this, activity_plantas.class);
                startActivity(intent);
                finish();
            }, 1500);

        }, 1500);
    }
}