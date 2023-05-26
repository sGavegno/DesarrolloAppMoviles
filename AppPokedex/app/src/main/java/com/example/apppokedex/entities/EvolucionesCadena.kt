package com.example.apppokedex.entities

data class EvolucionesCadena(
    val Id: Int? = null,
    val Nombre: String? = null,
    val Id_EvolucionaDe: Int? = null,
    val Variantes: Boolean? = null,
    val Detalles: List<EvolucionesDetalle>? = null,
    val PokemonVariantes: List<EvolucionesVariante>? = null
)
