package com.example.valess;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface HistorialDao {

    @Insert
    void insertar(HistorialRiego historial);

    @Query("SELECT * FROM historial_riego ORDER BY id DESC")
    List<HistorialRiego> obtenerTodo();

    @Query("DELETE FROM historial_riego")
    void eliminarTodo();
}