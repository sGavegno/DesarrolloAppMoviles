package com.example.apppokedex.entities

data class TablaTiposEfectividad(
    val Inmune: List<TablaTipoDetalle>? = null,
    val Debil: List<TablaTipoDetalle>? = null,
    val Efectivo: List<TablaTipoDetalle>? = null,
    val No_Efectivo: List<TablaTipoDetalle>? = null
)
