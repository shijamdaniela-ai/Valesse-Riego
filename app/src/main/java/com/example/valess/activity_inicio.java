package com.example.valess;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void irAPlantas(View view) {
        Intent intent = new Intent(this, Esp32_conexion.class);
        startActivity(intent);
    }
}