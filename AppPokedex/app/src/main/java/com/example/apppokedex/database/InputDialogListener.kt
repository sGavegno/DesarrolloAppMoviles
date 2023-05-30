package com.example.apppokedex.database

import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.Pokemon

interface InputDialogListener {
    fun onInputDone(input: Int)

    fun onInputMoteDone(poke: Pc, pokemon: Pokemon)

    fun onInputNivelDone(poke: Pc, pokemon: Pokemon)

    fun onInputGeneroDone(poke: Pc, pokemon: Pokemon)

    fun onInputHabilidadDone(poke: Pc, pokemon: Pokemon)
    fun onInputStringDone(input: String)

}