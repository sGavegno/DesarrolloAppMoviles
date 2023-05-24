package com.example.apppokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apppokedex.entities.PokemonUser
import com.example.apppokedex.entities.Pokemons

@Database(entities = [Pokemons::class, PokemonUser::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonUserDao(): PokemonUserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "myDB"
                    )
                        .addCallback(StartingPokemon(context))
                        .addCallback(StartingPokemonUser(context))
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries() // No es recomendable que se ejecute en el mainthread
                        .build()
                }
            }
            return INSTANCE
        }
    }
}