package com.example.apppokedex.database

import com.example.apppokedex.entities.Pc

interface InputDialogListener {
    fun onInputDone(input: Int)

    fun onInputMoteDone(poke: Pc)

    fun onInputNivelDone(poke: Pc)

    fun onInputGeneroDone(poke: Pc)

    fun onInputStringDone(input: String)

}