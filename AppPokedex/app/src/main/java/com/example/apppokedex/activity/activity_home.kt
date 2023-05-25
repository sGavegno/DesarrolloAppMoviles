package com.example.apppokedex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.apppokedex.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class activity_home : AppCompatActivity() {
    private lateinit var btnNavView : BottomNavigationView
    private lateinit var navHostFagment :NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navHostFagment = supportFragmentManager.findFragmentById(R.id.nav_Host) as NavHostFragment
        btnNavView = findViewById((R.id.btnNav))

        NavigationUI.setupWithNavController( btnNavView, navHostFagment.navController)
    }
}

