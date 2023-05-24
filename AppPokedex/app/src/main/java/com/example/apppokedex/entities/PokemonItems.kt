package com.example.apppokedex.entities

class PokemonItems(
    id: Int,
    nombre: String,
    idCategoriaItem: Int,
    precio: Int
){

    val id: Int
    val nombre: String
    val idCategoriaItem: Int
    val precio: Int

    init {
        this.id = id
        this.nombre = nombre
        this.idCategoriaItem = idCategoriaItem
        this.precio = precio
    }
}
