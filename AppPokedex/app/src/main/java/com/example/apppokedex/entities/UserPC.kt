package com.example.apppokedex.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userPc")
class UserPC(
    idUser: Int,
    idPokemon: Int ) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUser")
    var idUser: Int

    @ColumnInfo(name = "idPokemon")
    var idPokemon: Int

    init {
        this.idUser = idUser
        this.idPokemon = idPokemon
    }
}