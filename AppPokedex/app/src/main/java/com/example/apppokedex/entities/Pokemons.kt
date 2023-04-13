package com.example.apppokedex.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pokemons(
    var id : Int,
    var nombre : String,
    var tipo : String,
    var imgURL : String,
    var debilidad : String,
    var altura : String,
    var peso : String) : Parcelable {
    //var categoria : String,
    //var Habilidad : String,
    //var evolucion : String,                     //ver como implementar esto


}
