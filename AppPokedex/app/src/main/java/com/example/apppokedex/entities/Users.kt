package com.example.apppokedex.entities

class Users(
    id: String,
    userName: String,
    password : String,
    name : String,
    lastName : String,
    email : String,
    telefono : String,
    direccion : String,
    permisos : Int ) {

    var id: String
    var userName: String
    var password : String
    var name : String
    var lastName : String
    var email : String
    var telefono : String
    var direccion : String
    var permisos : Int

    init {
        this.id = id
        this.userName = userName
        this.password = password
        this.name = name
        this.lastName = lastName
        this.email = email
        this.telefono = telefono
        this.direccion = direccion
        this.permisos = permisos
    }
}