package com.example.valess;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

public class activity_historial extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView rvHistorial;
    private HistorialAdapter adapter;
    private Button btnVaciarHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        db = AppDatabase.getInstance(this);

        rvHistorial = findViewById(R.id.rvHistorial);
        if (rvHistorial != null) {
            rvHistorial.setLayoutManager(new LinearLayoutManager(this));
            adapter = new HistorialAdapter();
            rvHistorial.setAdapter(adapter);
        }

        btnVaciarHistorial = findViewById(R.id.btnVaciarHistorial);
        if (btnVaciarHistorial != null) {
            btnVaciarHistorial.setOnClickListener(v -> confirmarLimpiarHistorial());
        }

        cargarHistorialDesdeBD();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarHistorialDesdeBD();
    }

    private void cargarHistorialDesdeBD() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<HistorialRiego> lista = db.historialDao().obtenerTodo();
            runOnUiThread(() -> {
                if (adapter != null) {
                    adapter.setHistorial(lista);
                }
            });
        });
    }

    private void confirmarLimpiarHistorial() {
        new AlertDialog.Builder(this)
                .setTitle("Limpiar Historial")
                .setMessage("¿Estás seguro de que deseas borrar todos los registros de riego?")
                .setPositiveButton("Sí, borrar", (dialog, which) -> vaciarHistorialBD())
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void vaciarHistorialBD() {
        Executors.newSingleThreadExecutor().execute(() -> {
            db.historialDao().eliminarTodo();
            runOnUiThread(() -> {
                Toast.makeText(this, "Historial limpiado correctamente", Toast.LENGTH_SHORT).show();
                cargarHistorialDesdeBD();
            });
        });
    }
}