package com.example.apppokedex.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pokemons(
    var id : Int,
    var nombre : String,
    var tipo : String,
    var debilidad : String,
    var imgURL : String,
    var descripcion : String,
    var altura : String,
    var peso : String,
    var categoria : String,
    var habilidad : String,
    var generacion : Int,
    var evolucion : List<Int>) : Parcelable {
    //Nivel en el que evoluciona
        //mote : String,
        //parent : Int,     // id de la evolucion sigiente
        //child : Int,      // id de la evolucion previa
}

