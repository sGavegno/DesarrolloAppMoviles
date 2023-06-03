package com.example.apppokedex

import android.content.SharedPreferences
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.PcRepo
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.Usuario
import com.google.gson.Gson
import org.json.JSONObject

class PreferencesManager(private val sharedPreferences: SharedPreferences, private val gson: Gson) {


    fun getIdUser(): String{
        val uJson = sharedPreferences.getString("Usuario", null)
        val pureJsonUser = uJson?.let { JSONObject(it) }
        return pureJsonUser?.getString("id") ?: ""
    }

    fun getPermisosUser(): Boolean{
        val uJson = sharedPreferences.getString("Usuario", null)
        val pureJsonUser = uJson?.let { JSONObject(it) }
        return pureJsonUser?.getBoolean("permisos") ?: false
    }

    fun getNameUser(): String{
        val uJson = sharedPreferences.getString("Usuario", null)
        val pureJsonUser = uJson?.let { JSONObject(it) }
        return pureJsonUser?.getString("userName") ?: ""
    }

    fun getUserPokemon(idPokemon: Int): Pc? {
        val uJson = sharedPreferences.getString("Usuario", null)
        val user = gson.fromJson(uJson, Usuario::class.java)
        return user.pc?.filter{ item -> item.id == idPokemon }?.get(0)
    }

    fun getUserLogin(): Usuario{
        val uJson = sharedPreferences.getString("Usuario", null)
        return gson.fromJson(uJson, Usuario::class.java)
    }

    fun saveUser(user: Usuario) {
        val uJson = gson.toJson(user)
        sharedPreferences.edit().putString("Usuario", uJson).apply()
    }

    fun savePokemon(pokemon: Pokemon) {
        val uJson = gson.toJson(pokemon)
        sharedPreferences.edit().putString("Pokemon", uJson).apply()
    }

    fun getPokemon(): Pokemon {
        val uJson = sharedPreferences.getString("Pokemon", null)
        return gson.fromJson(uJson, Pokemon::class.java)
    }

    fun savePc(pc: PcRepo) {
        val uJson = gson.toJson(pc)
        sharedPreferences.edit().putString("Pc", uJson).apply()
    }

    fun getPc(): PcRepo? {
        val uJson = sharedPreferences.getString("Pc", null)
        return gson.fromJson(uJson, PcRepo::class.java)
    }

    fun savePokedex(pc: PokedexRepo) {
        val uJson = gson.toJson(pc)
        sharedPreferences.edit().putString("Pokedex", uJson).apply()
    }

    fun getPokedex(): PokedexRepo? {
        val uJson = sharedPreferences.getString("Pokedex", null)
        return gson.fromJson(uJson, PokedexRepo::class.java)
    }

}