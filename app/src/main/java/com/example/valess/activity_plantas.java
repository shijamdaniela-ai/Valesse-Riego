package com.example.valess;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executors;

public class activity_plantas extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView rvPlantas;
    private PlantaAdapter adapter;
    private FloatingActionButton btnAgregarPlanta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantas);

        db = AppDatabase.getInstance(this);

        rvPlantas = findViewById(R.id.rvPlantas);
        rvPlantas.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PlantaAdapter();
        rvPlantas.setAdapter(adapter);

        adapter.setOnItemClickListener(new PlantaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Planta planta) {
                Intent intent = new Intent(activity_plantas.this, activity_monitoreo.class);
                intent.putExtra("PLANTA_NOMBRE", planta.getNombre());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Planta planta) {
                eliminarPlantaBD(planta);
            }
        });

        btnAgregarPlanta = findViewById(R.id.btnAgregarPlanta);
        btnAgregarPlanta.setOnClickListener(v -> mostrarDialogoAgregarPlanta());

        cargarPlantasDesdeBD();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPlantasDesdeBD();
    }

    private void cargarPlantasDesdeBD() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Planta> lista = db.plantaDao().obtenerTodas();
            runOnUiThread(() -> adapter.setPlantas(lista));
        });
    }

    private void mostrarDialogoAgregarPlanta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar Nueva Planta");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_planta, null);
        EditText etNombre = viewInflated.findViewById(R.id.etNombrePlanta);
        EditText etUbicacion = viewInflated.findViewById(R.id.etUbicacionPlanta);
        EditText etHumedadOptima = viewInflated.findViewById(R.id.etHumedadOptima);

        builder.setView(viewInflated);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString().trim();
            String ubicacion = etUbicacion.getText().toString().trim();
            String humedadStr = etHumedadOptima.getText().toString().trim();

            if (!nombre.isEmpty() && !ubicacion.isEmpty() && !humedadStr.isEmpty()) {
                int humedadOptima = Integer.parseInt(humedadStr);
                Planta nuevaPlanta = new Planta(nombre, ubicacion, 0, humedadOptima, "08:00");

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.plantaDao().insertar(nuevaPlanta);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Planta guardada", Toast.LENGTH_SHORT).show();
                        cargarPlantasDesdeBD();
                    });
                });
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void eliminarPlantaBD(Planta planta) {
        Executors.newSingleThreadExecutor().execute(() -> {
            db.plantaDao().eliminar(planta);
            runOnUiThread(() -> {
                Toast.makeText(this, "Planta eliminada", Toast.LENGTH_SHORT).show();
                cargarPlantasDesdeBD();
            });
        });
    }

    public void iraMonitoreo(View view) {
        Intent intent = new Intent(this, activity_monitoreo.class);
        startActivity(intent);
    }
}