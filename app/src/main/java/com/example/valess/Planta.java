package com.example.valess;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plantas")
public class Planta {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private String ubicacion;
    private int humedadActual;
    private int humedadOptima;
    private String horaInicioRiego; // Ejemplo: "08:00 AM"

    public Planta(String nombre, String ubicacion, int humedadActual, int humedadOptima, String horaInicioRiego) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.humedadActual = humedadActual;
        this.humedadOptima = humedadOptima;
        this.horaInicioRiego = horaInicioRiego;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public int getHumedadActual() { return humedadActual; }
    public void setHumedadActual(int humedadActual) { this.humedadActual = humedadActual; }

    public int getHumedadOptima() { return humedadOptima; }
    public void setHumedadOptima(int humedadOptima) { this.humedadOptima = humedadOptima; }

    public String getHoraInicioRiego() { return horaInicioRiego; }
    public void setHoraInicioRiego(String horaInicioRiego) { this.horaInicioRiego = horaInicioRiego; }
}