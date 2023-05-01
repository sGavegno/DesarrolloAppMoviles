package com.example.apppokedex.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apppokedex.entities.Pokemons
import com.example.apppokedex.entities.PokemonUser
import com.example.apppokedex.entities.User

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemons ORDER BY idPokemon")
    fun fetchAllPokemon(): MutableList<Pokemons?>?

    @Query("SELECT * FROM Pokemons WHERE idPokemon = :idPokemon")
    fun fetchPokemonByIdPokemon(idPokemon: Int): Pokemons?

    @Query("SELECT * FROM Pokemons WHERE nombre = :nombre")
    fun fetchPokemonByName(nombre: String): Pokemons?

/*
    @Query("SELECT PokemonUser.idUser, PokemonUser.idPokemon, Pokemons.idPokemon " +
            "FROM Pokemons INNER JOIN Users ON Pokemons.idPokemon = PokemonUser.idPokemon " +
            "Where PokemonUser.idUser = :idUser")
    fun fetchPokemonUserById(idUser: Int): MutableList<Pokemons?>?
*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemons: Pokemons)

    @Update
    fun updatePokemon(pokemons: Pokemons)

    @Delete
    fun delete(pokemons: Pokemons)
}