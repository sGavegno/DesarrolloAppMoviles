package com.example.apppokedex.entities

class EvolucionesVariante(
    id: Int,
    nombre: String,
    altura: Int,
    peso: Int,
    expBase: Int
){

    val id: Int
    val nombre: String
    val altura: Int
    val peso: Int
    val expBase: Int

    init {
        this.id = id
        this.nombre = nombre
        this.altura = altura
        this.peso = peso
        this.expBase = expBase
    }
}