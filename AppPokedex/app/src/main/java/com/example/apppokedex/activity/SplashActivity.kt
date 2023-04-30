package com.example.apppokedex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.example.apppokedex.R
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.UserDao

class SplashActivity : AppCompatActivity() {

    lateinit var imgSplash : ImageView
    private val SPLASH_TIME_OUT:Long = 3000 // 3s

    private var db: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imgSplash = findViewById(R.id.imgSplash)
        Glide.with(this).load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png").into(imgSplash)

        db = AppDatabase.getInstance(this)

        Handler().postDelayed(
            {
                //Intent parametros origen y destino. Salta a la otra activity
                startActivity(Intent(this,MainActivity::class.java))
                finish()    //Destruye la activity.
            }
            , SPLASH_TIME_OUT)
    }
}