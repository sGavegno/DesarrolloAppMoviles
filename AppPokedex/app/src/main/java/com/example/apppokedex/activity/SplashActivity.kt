package com.example.apppokedex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide


import com.example.apppokedex.R

class SplashActivity : AppCompatActivity() {

    lateinit var imgSplash : ImageView
    private val SPLASH_TIME_OUT:Long = 3000 // 3s

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imgSplash = findViewById(R.id.imgSplash)
        Glide.with(this).load(R.drawable.pokedex_logo).into(imgSplash)

        Handler().postDelayed(
            {
                //Intent parametros origen y destino. Salta a la otra activity
                startActivity(Intent(this,MainActivity::class.java))
                finish()    //Destruye la activity.
            }
            , SPLASH_TIME_OUT)
    }
}