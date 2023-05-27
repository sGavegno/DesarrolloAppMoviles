package com.example.apppokedex.entities

data class TablaTiposPokemon(
    val Id_Tipo: Int? = null,
    val Tipo: String? = null,
    val Danio: TablaTiposEfectividad? = null
)

data class TablaTiposEfectividad(
    val Inmune: List<TablaTipoDetalle>? = null,
    val Debil: List<TablaTipoDetalle>? = null,
    val Efectivo: List<TablaTipoDetalle>? = null,
    val No_Efectivo: List<TablaTipoDetalle>? = null
)

data class TablaTipoDetalle(
    val Id_Tipo: Int? = null,
    val Tipo: String? = null
)