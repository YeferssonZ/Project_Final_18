package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val SPLASH_SCREEN_DELAY = 2000L


        val animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        val animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)

        val lblDe: TextView = findViewById(R.id.lblDe)
        val lblCodelia: TextView = findViewById(R.id.lblCodelia)
        val logo: LottieAnimationView = findViewById(R.id.lottie_layer_name)

        lblDe.startAnimation(animacion2)
        lblCodelia.startAnimation(animacion2)
        logo.startAnimation(animacion1)
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY)



    }
}