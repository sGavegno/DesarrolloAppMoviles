package com.example.apppokedex.entities

data class Pokemon(
    val Id: Int? = null,
    val Nombre: String? = null,
    val Tipo: List<PokemonTipo>? = null,
    val Altura: Int? = null,
    val Peso: Int? = null,
    val ExpBase: Int? = null,
    val Stats: List<PokemonStats>? = null,
    val Detalle: PokemonDetalle? = null,
    val Habilidades: List<PokemonHabilidad>? = null
)
