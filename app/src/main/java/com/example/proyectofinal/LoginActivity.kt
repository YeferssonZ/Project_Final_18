package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnImgresar = findViewById<Button>(R.id.btnIngresarLogin)
        btnImgresar.setOnClickListener{
            val intent = Intent(this, LogueoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}