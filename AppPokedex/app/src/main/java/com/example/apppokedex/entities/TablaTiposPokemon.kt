package com.example.apppokedex.entities

class TablaTiposPokemon(
    idTipo: Int,
    tipo: String,
    tablaTipos: TablaTiposEfectividad
){

    val idTipo: Int
    val tipo: String
    val tablaTipos: TablaTiposEfectividad

    init {
        this.idTipo = idTipo
        this.tipo = tipo
        this.tablaTipos = tablaTipos
    }
}
