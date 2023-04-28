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
    fun fetchAllUsers(): MutableList<PokemonUser?>?

    @Query("SELECT * FROM PokemonUser WHERE idUser = :idUser")
    fun fetchUserByIdUser(idUser: Int): PokemonUser?

    @Query("SELECT * FROM PokemonUser WHERE idPokemon = :idPokemon")
    fun fetchUserByIdPokemon(idPokemon: Int): PokemonUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(pokemonUser: PokemonUser)

    @Update
    fun updateUser(pokemonUser: PokemonUser)

    @Delete
    fun delete(pokemonUser: PokemonUser)

}