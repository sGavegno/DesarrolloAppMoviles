package com.example.apppokedex.entities

class EvolucionesCadena(
    id: Int,
    Nombre: String,
    idEvolucionaDe: Int?,
    Variantes: Boolean,
    detalles: List<EvolucionesDetalle>,
    pokemonVariantes: List<EvolucionesVariante>
){

    val id: Int
    val Nombre: String
    val idEvolucionaDe: Int?
    val Variantes: Boolean
    val detalles: List<EvolucionesDetalle>
    val pokemonVariantes: List<EvolucionesVariante>

    init {
        this.id = id
        this.Nombre = Nombre
        this.idEvolucionaDe = idEvolucionaDe
        this.Variantes = Variantes
        this.detalles = detalles
        this.pokemonVariantes = pokemonVariantes
    }
}