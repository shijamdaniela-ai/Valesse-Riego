package com.example.valess;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private TextInputEditText etUser, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Evento de clic en el botón de ingreso
        btnLogin.setOnClickListener(v -> realizarLoginLocal());
    }

    private void realizarLoginLocal() {
        String usuario = etUser.getText() != null ? etUser.getText().toString().trim() : "";
        String pin = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // Validaciones básicas de entrada
        if (TextUtils.isEmpty(usuario)) {
            etUser.setError("Ingrese su nombre de usuario");
            etUser.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pin) || pin.length() < 4) {
            etPassword.setError("Ingrese un PIN válido de 4 dígitos");
            etPassword.requestFocus();
            return;
        }

        // Validación local (puedes ajustar el PIN o usuario predeterminado para tus pruebas)
        Toast.makeText(this, "Sesión iniciada correctamente (Modo Offline)", Toast.LENGTH_SHORT).show();

        // Navegar a la pantalla principal de monitoreo
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish(); // Cerrar LoginActivity para que el usuario no regrese con el botón atrás
    }
}