package com.example.apppokedex.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.apppokedex.R
import com.example.apppokedex.entities.PokemonUser
import com.example.apppokedex.entities.Pokemons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

class StartingPokemonUser(private val context: Context): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingUsers", "Pre-populating database...")
            fillWithStartingUsers(context)
        }
    }

    private fun fillWithStartingUsers(context: Context) {
        val dao = AppDatabase.getInstance(context)?.pokemonUserDao()

        try {
            val pokemonsUser = loadJSONArray(context)              //Carga tabla ubicada en el json
            for (i in 0 until pokemonsUser.length()) {
                val item = pokemonsUser.getJSONObject(i)
                val pokemonUser = PokemonUser(
                    id = 0,
                    idUser = 0,
                    idPokemon = 0,
                    mote = item.getString("id"),
                    nivel = 0,
                    altura= item.getString("id"),
                    peso= item.getString("id"),
                    habilidad= item.getString("id")
                )
                dao?.insertPokemonUser(pokemonUser)
            }
        } catch (e: JSONException) {
            Log.e("fillWithStartingNotes", e.toString())
        }
    }

    private fun loadJSONArray(context: Context): JSONArray {
        val inputStream = context.resources.openRawResource(R.raw.pokemon_pc)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}