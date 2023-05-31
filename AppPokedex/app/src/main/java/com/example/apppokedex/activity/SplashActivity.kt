@file:Suppress("DEPRECATION")

package com.example.apppokedex.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.example.apppokedex.R


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var imgSplash : ImageView
    private val splashTimeout:Long = 3000 // 3s

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
            , splashTimeout)
    }
}