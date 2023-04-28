package com.example.apppokedex.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apppokedex.entities.Pokemons

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemons ORDER BY id")
    fun fetchAllUsers(): MutableList<Pokemons?>?

    @Query("SELECT * FROM Pokemons WHERE id = :id")
    fun fetchUserByIdPokemon(id: Int): Pokemons?

    @Query("SELECT * FROM Pokemons WHERE nombre = :nombre")
    fun fetchUserByName(nombre: String): Pokemons?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(pokemons: Pokemons)

    @Update
    fun updateUser(pokemons: Pokemons)

    @Delete
    fun delete(pokemons: Pokemons)


}