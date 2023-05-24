package com.example.apppokedex.entities

class PokemonDetalle(
    idCadenaEvolutiva: Int,
    idEvolucionaDe: Int?,
    felicidadBase: Int,
    ratioCaptura: Int,
    variantes: Boolean,
    tasaGenero: Int,
    idGeneracion: Int,
    bebe: Boolean,
    legendario: Boolean,
    mitico: Boolean
){

    var idCadenaEvolutiva: Int
    var idEvolucionaDe: Int?
    var felicidadBase: Int
    var ratioCaptura: Int
    var variantes: Boolean
    var tasaGenero: Int
    var idGeneracion: Int
    var bebe: Boolean
    var legendario: Boolean
    var mitico: Boolean

    init {
        this.idCadenaEvolutiva = idCadenaEvolutiva
        this.idEvolucionaDe = idEvolucionaDe
        this.felicidadBase = felicidadBase
        this.ratioCaptura = ratioCaptura
        this.variantes = variantes
        this.tasaGenero = tasaGenero
        this.idGeneracion = idGeneracion
        this.bebe = bebe
        this.legendario = legendario
        this.mitico = mitico
    }
}