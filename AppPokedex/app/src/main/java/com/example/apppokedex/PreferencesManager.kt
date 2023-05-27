package com.example.apppokedex

import android.content.SharedPreferences
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.Usuario
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

    fun getUserPokemon(idPokemon: Int): Pc? {
        val gson: Gson = Gson()
        val uJson = sharedPreferences.getString("UsuarioLogIn", null)
        val user = gson.fromJson(uJson, Usuario::class.java)
        return user.pokedex?.filter{ item -> item.idPokemon == idPokemon }?.get(0)
    }

    fun getUserLogin(): Usuario{
        val gson: Gson = Gson()
        val uJson = sharedPreferences.getString("UsuarioLogIn", null)
        return gson.fromJson(uJson, Usuario::class.java)
    }

    fun saveUser(user: Usuario) {
        val uJson = gson.toJson(user)
        sharedPreferences.edit().putString("UsuarioLogIn", uJson).apply()
    }



}