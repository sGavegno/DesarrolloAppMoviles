package com.example.navegacion.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User( var name : String, var lastName : String, var email : String, var password : String):Saludo,
    Parcelable {

    var age : Int = 0

    companion object{
        const val MAX_AGE = 100
    }

    override fun saludar(): String {
        return "Hola, mi nombre es $name $lastName"
    }
}