package com.example.apppokedex.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.apppokedex.R
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
        val dao = AppDatabase.getInstance(context)?.userDao()

        try {
            val users = loadJSONArray(context)              //Carga tabla ubicada en el json
            for (i in 0 until users.length()) {
                val item = users.getJSONObject(i)
                val user = User(
                    idUser = 0,
                    userName = item.getString("userName"),
                    password = item.getString("password"),
                    name = item.getString("name"),
                    lastName =item.getString("lastname"),
                    email = item.getString("email"),
                    telefono = item.getString("telefono"),
                    direccion = item.getString("direccion")
                )

                dao?.insertUser(user)
            }
            //idem para pokedex

        } catch (e: JSONException) {
            Log.e("fillWithStartingNotes", e.toString())
        }
    }

    private fun loadJSONArray(context: Context): JSONArray {
        val inputStream = context.resources.openRawResource(R.raw.users)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}