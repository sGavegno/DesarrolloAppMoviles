package com.example.apppokedex.entities

class PoekemonAtaques(
    nombre: String,
    precision: Int,
    pp: Int,
    poder: Int,
    prioridad: Int,
    id: Int,
    idTipo: Int,
    tipo: String
)
{

    val nombre: String
    val precision: Int
    val pp: Int
    val poder: Int
    val prioridad: Int
    val id: Int
    val idTipo: Int
    val tipo: String

    init {
        this.nombre = nombre
        this.precision = precision
        this.pp = pp
        this.poder = poder
        this.prioridad = prioridad
        this.id = id
        this.idTipo = idTipo
        this.tipo = tipo
    }
}
