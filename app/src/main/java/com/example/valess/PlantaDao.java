package com.example.valess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlantaDao {

    // C - Create (Insertar)
    @Insert
    void insertar(Planta planta);

    // R - Read (Consultar todas)
    @Query("SELECT * FROM plantas")
    List<Planta> obtenerTodas();

    @Query("SELECT * FROM plantas WHERE id = :id LIMIT 1")
    Planta obtenerPorId(int id);

    // U - Update (Actualizar)
    @Update
    void actualizar(Planta planta);

    // D - Delete (Eliminar)
    @Delete
    void eliminar(Planta planta);
}