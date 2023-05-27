package com.example.apppokedex.entities

data class Usuario(
    var id: String? = null,
    var userName: String? = null,
    var password: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var telefono: String? = null,
    var direccion: String? = null,
    var pokedex: List<Pc>? = null
)

data class Pc(
    var idPokemon: Int? = null,
    var nombre: String? = null,
    var tipo: List<Tipo>? = null,
    var inPc: Boolean? = null,
    var mote: String? = null,
    var nivel: Int? = null,
    var objeto: String? = null,
    var idObjeto: Int? = null,
    var genero: Boolean? = null,
    var ataque1: String? = null,
    var ataque2: String? = null,
    var ataque3: String? = null,
    var ataque4: String? = null
)

data class Tipo(
    var idTipo: Int? = null
)

