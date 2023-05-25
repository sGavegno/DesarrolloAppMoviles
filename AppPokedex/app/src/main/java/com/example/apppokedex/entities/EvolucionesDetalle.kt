package com.example.apppokedex.entities

class EvolucionesDetalle(
    idTipoEvolucion: Int,
    tipoEvolucion: String,
    nivel: Int,
    felicidad: Int?,
    belleza: Int?,
    afecto: Int?,
    horaDelDia: String,
    evolucionItem: PokemonItems?
){

    val idTipoEvolucion: Int
    val tipoEvolucion: String
    val nivel: Int
    val felicidad: Int?
    val belleza: Int?
    val afecto: Int?
    val horaDelDia: String
    val evolucionItem: PokemonItems?

    init {
        this.idTipoEvolucion = idTipoEvolucion
        this.tipoEvolucion = tipoEvolucion
        this.nivel = nivel
        this.felicidad = felicidad
        this.belleza = belleza
        this.afecto = afecto
        this.horaDelDia = horaDelDia
        this.evolucionItem = evolucionItem
    }
}