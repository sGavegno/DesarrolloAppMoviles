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

    @Query("SELECT * FROM Pokemons ORDER BY idPokemon")
    fun fetchAllPokemon(): MutableList<Pokemons?>?

    @Query("SELECT * FROM Pokemons WHERE idPokemon = :idPokemon")
    fun fetchPokemonByIdPokemon(idPokemon: Int): Pokemons?

    @Query("SELECT * FROM Pokemons WHERE nombre = :nombre")
    fun fetchPokemonByName(nombre: String): Pokemons?

    @Query("SELECT * FROM Pokemons WHERE child = :child")
    fun fetchPokemonByChild(child: Int): MutableList<Pokemons?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemons: Pokemons)

    @Update
    fun updatePokemon(pokemons: Pokemons)

    @Delete
    fun delete(pokemons: Pokemons)
}