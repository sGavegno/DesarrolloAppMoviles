package com.example.apppokedex.entities

data class Usuario(
    var id: String? = null,
    var userName: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var pokedex: MutableList<UserPokedex>? = null,
    var pc: MutableList<Pc>? = null
)

data class Pc(
    var id: Int? = null,
    var idPokemon: Int? = null,
    var mote: String? = null,
    var tipo: List<PokemonTipo>? = null,
    var nivel: Int? = null,
    var felicidad: Int? = null,
    var belleza: Int? = null,
    var afecto: Int? = null,
    var habilidad: String? = null,
    var objeto: String? = null,
    var idObjeto: Int? = null,
    var genero: Boolean? = null,
    var ataque1: String? = null,
    var ataque2: String? = null,
    var ataque3: String? = null,
    var ataque4: String? = null
)

data class UserPokedex(
    var idPokemon: Int? = null,
    var nombre: String? = null,
    var tipo: List<PokemonTipo>? = null,
)


