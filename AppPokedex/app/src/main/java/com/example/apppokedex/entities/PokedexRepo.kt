package com.example.apppokedex.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize
class PokedexRepo : Parcelable {

    @IgnoredOnParcel
    var pokemons = mutableListOf<Pokemon>()
}