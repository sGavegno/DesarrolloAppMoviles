package com.example.apppokedex.entities

class PokemonTipos(
    val name: String,
    val debilidad: List<String>,
    val fortaleza: List<String>
) {
    override fun toString(): String {
        return "$name - Debilidades: ${debilidad.joinToString()} | Fortalezas: ${fortaleza.joinToString()}"
    }
    init {
        val pokemonTipos = listOf(
            PokemonTipos("Planta", listOf("Fuego", "Hielo", "Veneno", "Volador", "Bicho", "Dragón", "Acero"), listOf("Agua", "Tierra", "Roca")),
            PokemonTipos("Fuego", listOf("Agua", "Tierra", "Roca"), listOf("Planta", "Hielo", "Bicho", "Acero")),
            PokemonTipos("Agua", listOf("Planta", "Eléctrico"), listOf("Fuego", "Tierra", "Roca")),
            PokemonTipos("Eléctrico", listOf("Tierra"), listOf("Agua", "Volador")),
            PokemonTipos("Normal", listOf("Lucha"), listOf()),
            PokemonTipos("Lucha", listOf("Psíquico", "Volador", "Hada"), listOf("Normal", "Hielo", "Roca")),
            PokemonTipos("Veneno", listOf("Tierra", "Psíquico"), listOf("Planta", "Hada")),
            PokemonTipos("Tierra", listOf("Agua", "Planta", "Hielo"), listOf("Fuego", "Eléctrico", "Veneno", "Roca", "Acero")),
            PokemonTipos("Volador", listOf("Eléctrico", "Hielo", "Roca"), listOf("Planta", "Lucha", "Bicho")),
            PokemonTipos("Psíquico", listOf("Bicho", "Fantasma", "Siniestro"), listOf("Lucha", "Veneno")),
            PokemonTipos("Roca", listOf("Agua", "Planta", "Lucha", "Tierra", "Acero"), listOf("Fuego", "Hielo", "Volador", "Bicho")),
            PokemonTipos("Fantasma", listOf("Normal", "Lucha"), listOf("Psíquico", "Fantasma")),
            PokemonTipos("Hielo", listOf("Fuego", "Lucha", "Roca", "Acero"), listOf("Planta", "Tierra", "Volador", "Dragón")),
            PokemonTipos("Bicho", listOf("Fuego", "Volador", "Roca"), listOf("Planta", "Psíquico", "Siniestro")),
            PokemonTipos("Dragón", listOf("Hielo", "Dragón", "Hada"), listOf("Dragón")),
            PokemonTipos("Siniestro", listOf("Lucha", "Bicho", "Hada"), listOf("Psíquico", "Fantasma")),
            PokemonTipos("Acero", listOf("Fuego", "Lucha", "Tierra"), listOf("Hielo", "Roca", "Hada")),
            PokemonTipos("Hada", listOf("Veneno", "Acero"), listOf("Lucha", "Dragón", "Siniestro")))
    }
}