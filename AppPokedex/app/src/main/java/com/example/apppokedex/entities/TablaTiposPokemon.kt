package com.example.apppokedex.entities

data class TablaTiposPokemon(
    val idTipo: Int? = null,
    val tipo: String? = null,
    val danio: TablaTiposEfectividad? = null
)

data class TablaTiposEfectividad(
    val inmune: List<TablaTipoDetalle>? = null,
    val debil: List<TablaTipoDetalle>? = null,
    val efectivo: List<TablaTipoDetalle>? = null,
    val noEfectivo: List<TablaTipoDetalle>? = null
)

data class TablaTipoDetalle(
    val idTipo: Int? = null,
    val tipo: String? = null
)