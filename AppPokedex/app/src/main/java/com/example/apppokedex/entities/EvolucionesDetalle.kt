package com.example.apppokedex.entities

data class EvolucionesDetalle(
    val Id_EvolucionItem: Int? = null,
    val Id_TipoEvolucion: Int? = null,
    val Nivel: Int? = null,
    val FelicIdad: Int? = null,
    val Belleza: Int? = null,
    val Afecto: Int? = null,
    val HoraDelDia: String? = null,
    val TipoEvolucion: EvolucionTipo? = null,
    val EvolucionItem: Int? = null
)
