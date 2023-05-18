package com.example.apppokedex.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.apppokedex.R
import com.example.apppokedex.entities.PokemonUser
import com.example.apppokedex.entities.Pokemons
import com.example.apppokedex.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

class StartingUsers(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingUsers", "Pre-populating database...")
            fillWithStartingUsers(context)
        }
    }

    private fun fillWithStartingUsers(context: Context) {
        val daoUser = AppDatabase.getInstance(context)?.userDao()
//       val daoPokemon = AppDatabase.getInstance(context)?.pokemonDao()
//        val daoPokemonUser = AppDatabase.getInstance(context)?.pokemonUserDao()
        try {
            val users = loadJSONArray(context,R.raw.user)              //Carga tabla ubicada en el json
            for (i in 0 until users.length()) {
                val item = users.getJSONObject(i)
                val user = User(
                    id = 0,
                    userName = item.getString("userName"),
                    password = item.getString("password"),
                    name = item.getString("name"),
                    lastName =item.getString("lastName"),
                    email = item.getString("email"),
                    telefono = item.getString("telefono"),
                    direccion = item.getString("direccion"),
                    permisos = item.getInt("permisos")
                )
                daoUser?.insertUser(user)
            }
        } catch (e: JSONException) {
            Log.e("fillWithStartingNotes", e.toString())
        }
    }
    private fun loadJSONArray(context: Context, id :Int): JSONArray {
        val inputStream = context.resources.openRawResource(id)
        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}