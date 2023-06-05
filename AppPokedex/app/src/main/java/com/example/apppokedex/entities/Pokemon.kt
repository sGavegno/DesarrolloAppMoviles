package com.example.apppokedex.entities


data class Pokemon(
    val id: Int? = null,
    val nombre: String? = null,
    val tipo: List<PokemonTipo>? = null,
    val altura: Int? = null,
    val peso: Int? = null,
    val expBase: Int? = null,
    val stats: List<PokemonStats>? = null,
    val detalle: PokemonDetalle? = null,
    val habilidades: List<PokemonHabilidad>? = null
)

data class PokemonTipo(
    val idTipo: Int? = null,
    val detalle: PokemonTipoDetalle? = null
)

data class PokemonTipoDetalle(
    val nombre: String? = null
)

data class PokemonStats(
    var statsBase: Int? = null,
    var idStats: Int? = null,
    var detalle: PokemonStatsDetalle? = null
)
data class PokemonStatsDetalle(
    val nombre: String? = null
)

data class PokemonDetalle(
    val bebe: Boolean? = null,
    val felicidadBase: Int? = null,
    val legendario: Boolean? = null,
    val mitico: Boolean? = null,
    val ratioCaptura: Int? = null,
    val tasaGenero: Int? = null,
    val variantes: Boolean? = null,
    val idGeneracion: Int? = null,
    val idCadenaEvolutiva: Int? = null,
    val idEvolucionaDe: Int? = null,
)

data class PokemonHabilidad(
    val detalle: PokemonHabilidadDetalle? = null,
    val idHabilidades: Int? = null
)

data class PokemonHabilidadDetalle(
    var nombre: String? = null
)