package com.example.valess;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "historial_riego")
public class HistorialRiego {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombrePlanta;
    private String fechaHora;
    private String tipoRiego;

    public HistorialRiego(String nombrePlanta, String fechaHora, String tipoRiego) {
        this.nombrePlanta = nombrePlanta;
        this.fechaHora = fechaHora;
        this.tipoRiego = tipoRiego;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombrePlanta() { return nombrePlanta; }
    public void setNombrePlanta(String nombrePlanta) { this.nombrePlanta = nombrePlanta; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getTipoRiego() { return tipoRiego; }
    public void setTipoRiego(String tipoRiego) { this.tipoRiego = tipoRiego; }
}