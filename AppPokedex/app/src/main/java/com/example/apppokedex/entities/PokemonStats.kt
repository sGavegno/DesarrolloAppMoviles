package com.example.apppokedex.entities

class PokemonStats(
    idStats: Int,
    nombre: String,
    statsBase: Int
){

    var statsBase: Int
    var idStats: Int
    var nombre: String

    init {
        this.idStats = idStats
        this.nombre = nombre
        this.statsBase = statsBase
    }
}