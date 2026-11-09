package com.example.valess;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_monitoreo extends AppCompatActivity {

    private Spinner spPlantasMonitoreo;
    private TextView tvEstadoHumedad, tvInfoProgramacion;
    private Button btnActivarRiego, btnConfigurar, btnVerHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        // 1. Enlazar componentes de la interfaz XML
        spPlantasMonitoreo = findViewById(R.id.spPlantasMonitoreo);
        tvEstadoHumedad = findViewById(R.id.tvEstadoHumedad);
        tvInfoProgramacion = findViewById(R.id.tvInfoProgramacion);
        btnActivarRiego = findViewById(R.id.btnActivarRiego);
        btnConfigurar = findViewById(R.id.btnConfigurar);
        btnVerHistorial = findViewById(R.id.btnVerHistorial);

        // 2. Configurar el adaptador para el Spinner de plantas
        String[] listaPlantas = {"Rosa", "Cáctus", "Orquídea", "Tomate", "Follaje General"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaPlantas);
        spPlantasMonitoreo.setAdapter(adapter);

        // 3. Lógica del botón para iniciar el riego automático con animación en el círculo
        btnActivarRiego.setOnClickListener(v -> {
            btnActivarRiego.setEnabled(false); // Desactivar para evitar múltiples clics
            Toast.makeText(this, "💧 Iniciando sistema de riego automático...", Toast.LENGTH_SHORT).show();

            // Simulamos que la humedad sube progresivamente desde el valor actual hasta el umbral (80%)
            final int[] humedadActual = {42};
            Handler handler = new Handler(Looper.getMainLooper());

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (humedadActual[0] <= 80) {
                        tvEstadoHumedad.setText(humedadActual[0] + "%");
                        humedadActual[0]++;
                        handler.postDelayed(this, 80); // Sube 1% cada 80 milisegundos (efecto fluido para el video)
                    } else {
                        Toast.makeText(activity_monitoreo.this, "¡Riego completado! Humedad óptima alcanzada.", Toast.LENGTH_LONG).show();
                        btnActivarRiego.setEnabled(true);
                    }
                }
            };
            handler.post(runnable);
        });
    }

    // 4. Método para ir a Configuración (llamado desde el XML con android:onClick="irAConfiguracion")
    public void irAConfiguracion(View view) {
        Intent intent = new Intent(activity_monitoreo.this, activity_configuracion.class);
        startActivity(intent);
    }

    // 5. Método para ir al Historial (llamado desde el XML con android:onClick="irAHistorial")
    public void irAHistorial(View view) {
        Intent intent = new Intent(activity_monitoreo.this, activity_historial.class);
        startActivity(intent);
    }
}