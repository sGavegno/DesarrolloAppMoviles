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
    var habilidad : String) : Parcelable {
    //var evolucion : String,                     //ver como implementar esto


}
