package com.example.examen_ii

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuarios = findViewById<Button>(R.id.btnRecyclerView)
        val creditos = findViewById<Button>(R.id.btnCreditos)
        val salir = findViewById<Button>(R.id.btnSalir)

        usuarios.setOnClickListener {
            val intento1 = Intent(this, UsersActivity::class.java)
            startActivity(intento1)
        }

        creditos.setOnClickListener {
            val intento2 = Intent(this, CreditosActivity::class.java)
            startActivity(intento2)
        }
        salir.setOnClickListener {
            finishAffinity()
        }

    }
}