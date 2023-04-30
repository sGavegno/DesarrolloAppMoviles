package com.example.apppokedex.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(
    id: Int,
    userName: String,
    password : String,
    name : String,
    lastName : String,
    email : String,
    telefono : String,
    direccion : String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

    @ColumnInfo(name = "userName")
    var userName: String

    @ColumnInfo(name = "password")
    var password: String

    @ColumnInfo(name = "name")
    var name: String

    @ColumnInfo(name = "lastname")
    var lastName: String

    @ColumnInfo(name = "email")
    var email: String

    @ColumnInfo(name = "telefono")
    var telefono: String

    @ColumnInfo(name = "direccion")
    var direccion: String
    init {
        this.id = id
        this.userName = userName
        this.password = password
        this.name = name
        this.lastName = lastName
        this.email = email
        this.telefono = telefono
        this.direccion = direccion
    }

}