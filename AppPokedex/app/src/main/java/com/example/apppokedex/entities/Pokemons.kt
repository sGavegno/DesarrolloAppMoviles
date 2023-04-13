package com.example.apppokedex.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pokemons(
    var id : Int,
    var nombre : String,
    var tipo : String,
    var imgURL : String) : Parcelable {


}