package com.example.apppokedex.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize
class PcRepo : Parcelable {

    @IgnoredOnParcel
    var pc = mutableListOf<Pc>()
}