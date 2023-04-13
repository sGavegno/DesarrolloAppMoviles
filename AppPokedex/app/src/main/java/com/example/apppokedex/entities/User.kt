package com.example.apppokedex.entities

class User(
    var name : String,
    var lastName : String,
    var email : String,
    var password : String) {

    var age : Int = 0

    companion object{
        const val MAX_AGE = 100
    }
}