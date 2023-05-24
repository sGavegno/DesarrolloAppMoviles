package com.example.apppokedex.entities

class TablaTiposEfectividad(
    inmune: List<PokemonTipo>,
    debil: List<PokemonTipo>,
    efectivo: List<PokemonTipo>,
    noEfectivo: List<PokemonTipo>
){

    val inmune: List<PokemonTipo>
    val debil: List<PokemonTipo>
    val efectivo: List<PokemonTipo>
    val noEfectivo: List<PokemonTipo>

    init {
        this.inmune = inmune
        this.debil = debil
        this.efectivo = efectivo
        this.noEfectivo = noEfectivo
    }
}