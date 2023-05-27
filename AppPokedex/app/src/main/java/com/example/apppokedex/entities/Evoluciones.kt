package com.example.apppokedex.entities

data class Evoluciones(
    val id: Int? = null,
    val cadenaEvolutiva: List<EvolucionesCadena>? = null
)

data class EvolucionesCadena(
    val id: Int? = null,
    val nombre: String? = null,
    val idEvolucionaDe: Int? = null,
    val variantes: Boolean? = null,
    val detalles: List<EvolucionesDetalle>? = null,
    val pokemonVariantes: List<EvolucionesVariante>? = null
)

data class EvolucionesDetalle(
    val idEvolucionItem: Int? = null,
    val idTipoEvolucion: Int? = null,
    val nivel: Int? = null,
    val felicIdad: Int? = null,
    val belleza: Int? = null,
    val afecto: Int? = null,
    val horaDelDia: String? = null,
    val tipoEvolucion: EvolucionTipo? = null,
    val evolucionItem: Int? = null
)

data class EvolucionTipo(
    val id: Int? = null,
    val nombre: String? = null
)

data class EvolucionesVariante(
    val id: Int? = null,
    val nombre: String? = null,
    val altura: Int? = null,
    val peso: Int? = null,
    val expBase: Int? = null
)