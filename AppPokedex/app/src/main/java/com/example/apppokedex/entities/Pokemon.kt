package com.example.apppokedex.entities

class Pokemon(
    id: Int,
    nombre: String,
    tipo: List<PokemonTipo>,
    altura: Int,
    peso: Int,
    expBase: Int,
    stats: List<PokemonStats>,
    detalle: PokemonDetalle,
    habilidades: List<PokemonHabilidad>
){

    var id: Int
    var nombre: String
    var tipo: List<PokemonTipo>
    var altura: Int
    var peso: Int
    var expBase: Int
    var stats: List<PokemonStats>
    var detalle: PokemonDetalle
    var habilidades: List<PokemonHabilidad>

    init {
        this.id = id
        this.nombre = nombre
        this.tipo = tipo
        this.altura = altura
        this.peso = peso
        this.expBase = expBase
        this.stats = stats
        this.detalle = detalle
        this.habilidades = habilidades
    }
}



