package com.example.apppokedex.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pokemonuser")
class PokemonUser(
    id : Int,
    idUser : Int,
    idPokemon : Int,
    mote : String,
    nivel : Int,
    altura : String,
    peso : String,
    habilidad : String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

    @ColumnInfo(name = "idUser")
    var idUser: Int

    @ColumnInfo(name = "idPokemon")
    var idPokemon: Int

    @ColumnInfo(name = "mote")
    var mote: String

    @ColumnInfo(name = "nivel")
    var nivel: Int

    @ColumnInfo(name = "altura")
    var altura: String

    @ColumnInfo(name = "peso")
    var peso: String

    @ColumnInfo(name = "habilidad")
    var habilidad: String

    init {
        this.id = id
        this.idUser = idUser
        this.idPokemon = idPokemon
        this.mote = mote
        this.nivel = nivel
        this.altura = altura
        this.peso = peso
        this.habilidad = habilidad
    }

}