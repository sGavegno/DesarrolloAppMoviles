package com.example.apppokedex.entities

data class PoekemonAtaques(
    val Nombre: String? = null,
    val Precision: Int? = null,
    val pp: Int? = null,
    val Poder: Int? = null,
    val Prioridad: Int? = null,
    val id: Int? = null,
    val id_Tipo: Int? = null,
    val Tipo: PoekemonAtaquesTipoDetalle? = null
)

data class PoekemonAtaquesTipoDetalle(
    val Nombre: String? = null
)
