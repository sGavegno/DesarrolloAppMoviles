package com.example.apppokedex.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pokemons
import com.example.apppokedex.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

class StartingPokemon(private val context: Context): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingUsers", "Pre-populating database...")
            fillWithStartingUsers(context)
        }
    }

    private fun fillWithStartingUsers(context: Context) {
        val dao = AppDatabase.getInstance(context)?.pokemonDao()

        try {
            val pokemons = loadJSONArray(context)              //Carga tabla ubicada en el json
            for (i in 0 until pokemons.length()) {
                val item = pokemons.getJSONObject(i)
                val pokemon = Pokemons(
                    id = 0,
                    idPokemon = 0,
                    nombre= item.getString("id"),
                    tipo= item.getString("id"),
                    debilidad= item.getString("id"),
                    imgURL= item.getString("id"),
                    descripcion= item.getString("id"),
                    altura= item.getString("id"),
                    peso= item.getString("id"),
                    categoria= item.getString("id"),
                    habilidad= item.getString("id"),
                    generacion= 0,
                    child = 0,
                    parent = 0
                )
                dao?.insertPokemon(pokemon)
            }
        } catch (e: JSONException) {
            Log.e("fillWithStartingNotes", e.toString())
        }
    }

    private fun loadJSONArray(context: Context): JSONArray {
        val inputStream = context.resources.openRawResource(R.raw.pokedex)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}