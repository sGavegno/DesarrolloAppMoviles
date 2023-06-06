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
    var nombre: String? =null,
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
    var ataque4: String? = null,
    var statsBase: List<PokemonStats>? = null,
    var stats: Estadisticas? = null,
    var puntoEsfuerzo: Estadisticas? = null,      // solo lo aumento con vitaminas
    var iV: Int? = null,                                    //IV (Valor indvidual)  random de 0 a 31
    var naturaleza: Naturaleza? = null,             //naturaleza            Crear tabla de naturaleza       son 25 y se asigna de forma aleatoria
    var descripcion: String? = null

//estadística = ((2 * Estadística base + IV + (Puntos de esfuerzo / 4)) * (Nivel / 100) + 5) * Naturaleza

//Puntos de esfuerzo    las vitaminas aumentan en 10 los puntos de una estadistica. 255 puntos maximos por estadisticas y un total de 510
)

data class UserPokedex(
    var idPokemon: Int? = null,
    var nombre: String? = null,
    var tipo: List<PokemonTipo>? = null,
)

data class Estadisticas(
    var hp: Int? = null,
    var ataque: Int? = null,
    var defensa: Int? = null,
    var atEsp: Int? = null,
    var defEsp:Int? = null,
    var velocidad: Int? = null
)

data class Naturaleza(
    var id: Int? = null,
    var nombre: String? = null,
    var stats: List<NaturalezaEfecto>? = null
)

data class NaturalezaEfecto(
    val idStats :Int? = null,
    val nombre :String? = null,
    val multiplicador: Float? = null
)