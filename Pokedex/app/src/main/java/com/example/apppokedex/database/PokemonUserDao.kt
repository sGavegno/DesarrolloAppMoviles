package com.example.apppokedex.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apppokedex.entities.PokemonUser

@Dao
interface PokemonUserDao {

    @Query("SELECT * FROM PokemonUser ORDER BY idUser")
    fun fetchAllPokemonUser(): MutableList<PokemonUser?>?

    @Query("SELECT * FROM PokemonUser WHERE (idUser = :idUser and idPokemon = :idPokemon)")
    fun fetchPokemonUserById(idUser: Int, idPokemon: Int): PokemonUser?

    @Query("SELECT * FROM PokemonUser WHERE idUser = :idUser ORDER BY  idPokemon")
    fun fetchALLPokemonUserByIdUser(idUser: Int): MutableList<PokemonUser?>?

    @Query("SELECT * FROM PokemonUser WHERE idPokemon = :idPokemon")
    fun fetchPokemonUserByIdPokemon(idPokemon: Int): PokemonUser?

    @Query("SELECT * FROM PokemonUser WHERE (idUser = :idUser and idPokemon = :idPokemon)")
    fun fetchPokemonUserByPokemon(idUser: Int, idPokemon: Int): PokemonUser?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonUser(pokemonUser: PokemonUser)

    @Update
    fun updatePokemonUser(pokemonUser: PokemonUser)

    @Delete
    fun delete(pokemonUser: PokemonUser)

}