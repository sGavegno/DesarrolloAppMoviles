package com.example.apppokedex.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pokemonuser")
class PokemonUser(
    id : Int,
    idUser : String,
    idPokemon : Int,
    mote : String,
    nivel : Int,
    altura : String,
    peso : String,
    descripcion : String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

    @ColumnInfo(name = "idUser")
    var idUser: String

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

    @ColumnInfo(name = "descripcion")
    var descripcion: String

    init {
        this.id = id
        this.idUser = idUser
        this.idPokemon = idPokemon
        this.mote = mote
        this.nivel = nivel
        this.altura = altura
        this.peso = peso
        this.descripcion = descripcion
    }

}