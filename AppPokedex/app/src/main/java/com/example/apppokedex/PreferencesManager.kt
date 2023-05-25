package com.example.apppokedex

import android.content.SharedPreferences
import com.example.apppokedex.entities.Usuarios
import com.google.gson.Gson
import org.json.JSONObject

class PreferencesManager(private val sharedPreferences: SharedPreferences, private val gson: Gson) {


    fun getIdUser(): String{
        val gson: Gson = Gson()
        val uJson = sharedPreferences.getString("UsuarioLogIn", null)
        val pureJsonUser = uJson?.let { JSONObject(it) }
        return pureJsonUser?.getString("id") ?: ""
    }

    fun getPermisosUser(): Boolean{
        val gson: Gson = Gson()
        val uJson = sharedPreferences.getString("UsuarioLogIn", null)
        val pureJsonUser = uJson?.let { JSONObject(it) }
        return pureJsonUser?.getBoolean("permisos") ?: false
    }

    fun getNameUser(): String{
        val gson: Gson = Gson()
        val uJson = sharedPreferences.getString("UsuarioLogIn", null)
        val pureJsonUser = uJson?.let { JSONObject(it) }
        return pureJsonUser?.getString("userName") ?: ""
    }

    fun getPasswordUser(): String{
        val gson: Gson = Gson()
        val uJson = sharedPreferences.getString("UsuarioLogIn", null)
        val pureJsonUser = uJson?.let { JSONObject(it) }
        return pureJsonUser?.getString("password") ?: ""
    }

    fun getUserLogin(): Usuarios{
        val gson: Gson = Gson()
        val uJson = sharedPreferences.getString("UsuarioLogIn", null)
        return gson.fromJson(uJson, Usuarios::class.java)
    }

    fun saveUser(user: Usuarios) {
        val uJson = gson.toJson(user)
        sharedPreferences.edit().putString("UsuarioLogIn", uJson).apply()
    }

}