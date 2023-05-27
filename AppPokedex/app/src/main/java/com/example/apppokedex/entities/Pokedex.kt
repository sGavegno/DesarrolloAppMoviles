package com.example.apppokedex.entities

data class Pokedex(
    val id: Int? = null,
    val name: String? = null,
    var tipo: List<Tipo>? = null
)
