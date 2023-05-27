package com.example.apppokedex.entities

data class Pokemon(
    val Id: Int? = null,
    val Nombre: String? = null,
    val Tipo: List<PokemonTipo>? = null,
    val Altura: Int? = null,
    val Peso: Int? = null,
    val ExpBase: Int? = null,
    val Stats: List<PokemonStats>? = null,
    val Detalle: PokemonDetalle? = null,
    val Habilidades: List<PokemonHabilidad>? = null
)

data class PokemonTipo(
    val Id_Tipo: Int? = null,
    val Detalle: PokemonTipoDetalle? = null
)

data class PokemonTipoDetalle(
    val Nombre: String? = null
)

data class PokemonStats(
    val Stats_Base: Int? = null,
    val Id_Stats: Int? = null,
    val Detalle: PokemonStatsDetalle? = null
)
data class PokemonStatsDetalle(
    val Nombre: String? = null
)

data class PokemonDetalle(
    val Bebe: Boolean? = null,
    val FelicidadBase: Int? = null,
    val Legendario: Boolean? = null,
    val Mitico: Boolean? = null,
    val RatioCaptura: Int? = null,
    val TasaGenero: Int? = null,
    val Variantes: Boolean? = null,
    val IdGeneracion: Int? = null,
    val Id_CadenaEvolutiva: Int? = null,
    val Id_EvolucionaDe: Int? = null,
)

data class PokemonHabilidad(
    val Detalle: PokemonHabilidadDetalle? = null,
    val Id_Habilidades: Int? = null
)

data class PokemonHabilidadDetalle(
    val Nombre: String? = null
)