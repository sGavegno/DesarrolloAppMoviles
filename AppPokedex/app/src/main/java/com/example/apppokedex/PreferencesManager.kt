package com.example.apppokedex

import android.content.SharedPreferences
import android.location.Location
import com.example.apppokedex.entities.Evoluciones
import com.example.apppokedex.entities.EvolucionesCadena
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.PcRepo
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.TablaTiposRepo
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

    fun savePcPokemon(pc: Pc) {
        val uJson = gson.toJson(pc)
        sharedPreferences.edit().putString("PcPokemon", uJson).apply()
    }

    fun getPcPokemon(): Pc {
        val uJson = sharedPreferences.getString("PcPokemon", null)
        return gson.fromJson(uJson, Pc::class.java)
    }

    fun saveLocation(ubicacion: Location) {
        val uJson = gson.toJson(ubicacion)
        sharedPreferences.edit().putString("Location", uJson).apply()
    }

    fun getLocation(): Location {
        val uJson = sharedPreferences.getString("Location", null)
        return gson.fromJson(uJson, Location::class.java)
    }

    fun saveUbicacion(ubicacion: String) {
        val uJson = gson.toJson(ubicacion)
        sharedPreferences.edit().putString("Ubicacion", uJson).apply()
    }

    fun getUbicacion(): String {
        val uJson = sharedPreferences.getString("Ubicacion", null)
        return gson.fromJson(uJson, String::class.java)
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

    fun saveEvoluciones(evoluciones: Evoluciones) {
        val uJson = gson.toJson(evoluciones)
        sharedPreferences.edit().putString("Evoluciones", uJson).apply()
    }

    fun getEvoluciones(): Evoluciones {
        val uJson = sharedPreferences.getString("Evoluciones", null)
        return gson.fromJson(uJson, Evoluciones::class.java)
    }

    fun saveEvolucionA(evolucionesA: Pokemon?) {
        val uJson = gson.toJson(evolucionesA)
        sharedPreferences.edit().putString("EvolucionA", uJson).apply()
    }

    fun getEvolucionA(): Pokemon {
        val uJson = sharedPreferences.getString("EvolucionA", null)
        return gson.fromJson(uJson, Pokemon::class.java)
    }
    fun saveTablaTiposPokemon(tipo: TablaTiposRepo) {
        val uJson = gson.toJson(tipo)
        sharedPreferences.edit().putString("TablaTiposPokemon", uJson).apply()
    }

    fun getTablaTiposPokemon(): TablaTiposRepo {
        val uJson = sharedPreferences.getString("TablaTiposPokemon", null)
        return gson.fromJson(uJson, TablaTiposRepo::class.java)
    }

}