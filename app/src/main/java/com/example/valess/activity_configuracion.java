package com.example.valess;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class activity_configuracion extends AppCompatActivity {

    private AppDatabase db;
    private Spinner spPlantasConfig;
    private EditText etHumedadOptimaConfig;
    private TextView tvHoraSeleccionada;
    private Button btnSeleccionarHora, btnGuardarConfig;

    private List<Planta> listaPlantas = new ArrayList<>();
    private Planta plantaSeleccionada;
    private String horaProgramada = "08:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        db = AppDatabase.getInstance(this);

        spPlantasConfig = findViewById(R.id.spPlantasConfig);
        etHumedadOptimaConfig = findViewById(R.id.etHumedadOptimaConfig);
        tvHoraSeleccionada = findViewById(R.id.tvHoraSeleccionada);
        btnSeleccionarHora = findViewById(R.id.btnSeleccionarHora);
        btnGuardarConfig = findViewById(R.id.btnGuardarConfig);

        btnSeleccionarHora.setOnClickListener(v -> abrirSelectorHora());
        btnGuardarConfig.setOnClickListener(v -> guardarConfiguracionPlanta());

        cargarPlantas();
    }

    private void cargarPlantas() {
        Executors.newSingleThreadExecutor().execute(() -> {
            listaPlantas = db.plantaDao().obtenerTodas();
            List<String> nombres = new ArrayList<>();

            for (Planta p : listaPlantas) {
                nombres.add(p.getNombre());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        nombres
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPlantasConfig.setAdapter(adapter);

                spPlantasConfig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        plantaSeleccionada = listaPlantas.get(position);
                        etHumedadOptimaConfig.setText(String.valueOf(plantaSeleccionada.getHumedadOptima()));
                        horaProgramada = plantaSeleccionada.getHoraInicioRiego() != null ?
                                plantaSeleccionada.getHoraInicioRiego() : "08:00";
                        tvHoraSeleccionada.setText("Hora programada: " + horaProgramada);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
        });
    }

    private void abrirSelectorHora() {
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            horaProgramada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            tvHoraSeleccionada.setText("Hora programada: " + horaProgramada);
        }, hora, minuto, true);

        timePicker.show();
    }

    private void guardarConfiguracionPlanta() {
        if (plantaSeleccionada == null) return;

        String humedadStr = etHumedadOptimaConfig.getText().toString().trim();
        if (humedadStr.isEmpty()) {
            Toast.makeText(this, "Ingresa un nivel de humedad válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int nuevaHumedadOptima = Integer.parseInt(humedadStr);
        plantaSeleccionada.setHumedadOptima(nuevaHumedadOptima);
        plantaSeleccionada.setHoraInicioRiego(horaProgramada);

        Executors.newSingleThreadExecutor().execute(() -> {
            db.plantaDao().actualizar(plantaSeleccionada);
            runOnUiThread(() ->
                    Toast.makeText(this, "Configuración automática guardada", Toast.LENGTH_SHORT).show()
            );
        });
    }
}