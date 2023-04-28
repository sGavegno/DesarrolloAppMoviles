package com.example.apppokedex.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "pokemons")
class Pokemons(
    id : Int,
    nombre : String,
    tipo : String,
    debilidad : String,
    imgURL : String,
    descripcion : String,
    altura : String,
    peso : String,
    categoria : String,
    habilidad : String,
    generacion : Int,
    child : Int,      // id de la evolucion previa
    parent : Int     // id de la evolucion sigiente
    ) {
    //Nivel en el que evoluciona

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

    @ColumnInfo(name = "nombre")
    var nombre: String

    @ColumnInfo(name = "tipo")
    var tipo: String

    @ColumnInfo(name = "debilidad")
    var debilidad: String

    @ColumnInfo(name = "imgURL")
    var imgURL: String

    @ColumnInfo(name = "descripcion")
    var descripcion: String

    @ColumnInfo(name = "altura")
    var altura: String

    @ColumnInfo(name = "peso")
    var peso: String

    @ColumnInfo(name = "categoria")
    var categoria: String

    @ColumnInfo(name = "habilidad")
    var habilidad: String

    @ColumnInfo(name = "parent")
    var parent: Int

    @ColumnInfo(name = "child")
    var child: Int

    @ColumnInfo(name = "generacion")
    var generacion: Int

    init {
        this.id = id
        this.nombre = nombre
        this.tipo = tipo
        this.debilidad = debilidad
        this.imgURL = imgURL
        this.descripcion = descripcion
        this.altura = altura
        this.peso = peso
        this.categoria = categoria
        this.habilidad = habilidad
        this.child = child
        this.parent = parent
        this.generacion = generacion
    }
}

