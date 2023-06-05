package com.example.apppokedex.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
class PokemonRepo : Parcelable {

    @IgnoredOnParcel
    var pokemon = mutableListOf<Pokemon>()
}