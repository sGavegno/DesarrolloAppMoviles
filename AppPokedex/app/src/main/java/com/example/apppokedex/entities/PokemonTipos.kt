package com.example.apppokedex.entities

class PokemonTipos(
    val idTipo : Int,
    val name: String,
    val debilidad: List<String>,
    val fortaleza: List<String>
    //var noEfectivo: List<String>,
    //var inmune: List<String>
) {
    override fun toString(): String {
        return "$name - Debilidades: ${debilidad.joinToString()} | Fortalezas: ${fortaleza.joinToString()}"
    }
    init {
        val pokemonTipos = listOf(
            PokemonTipos(1,"Planta", listOf("Fuego", "Hielo", "Veneno", "Volador", "Bicho", "Dragón", "Acero"), listOf("Agua", "Tierra", "Roca")),
            PokemonTipos(2,"Fuego", listOf("Agua", "Tierra", "Roca"), listOf("Planta", "Hielo", "Bicho", "Acero")),
            PokemonTipos(3,"Agua", listOf("Planta", "Eléctrico"), listOf("Fuego", "Tierra", "Roca")),
            PokemonTipos(4,"Eléctrico", listOf("Tierra"), listOf("Agua", "Volador")),
            PokemonTipos(5,"Normal", listOf("Lucha"), listOf()),
            PokemonTipos(6,"Lucha", listOf("Psíquico", "Volador", "Hada"), listOf("Normal", "Hielo", "Roca")),
            PokemonTipos(7,"Veneno", listOf("Tierra", "Psíquico"), listOf("Planta", "Hada")),
            PokemonTipos(8,"Tierra", listOf("Agua", "Planta", "Hielo"), listOf("Fuego", "Eléctrico", "Veneno", "Roca", "Acero")),
            PokemonTipos(9,"Volador", listOf("Eléctrico", "Hielo", "Roca"), listOf("Planta", "Lucha", "Bicho")),
            PokemonTipos(10,"Psíquico", listOf("Bicho", "Fantasma", "Siniestro"), listOf("Lucha", "Veneno")),
            PokemonTipos(11,"Roca", listOf("Agua", "Planta", "Lucha", "Tierra", "Acero"), listOf("Fuego", "Hielo", "Volador", "Bicho")),
            PokemonTipos(12,"Fantasma", listOf("Normal", "Lucha"), listOf("Psíquico", "Fantasma")),
            PokemonTipos(13,"Hielo", listOf("Fuego", "Lucha", "Roca", "Acero"), listOf("Planta", "Tierra", "Volador", "Dragón")),
            PokemonTipos(14,"Bicho", listOf("Fuego", "Volador", "Roca"), listOf("Planta", "Psíquico", "Siniestro")),
            PokemonTipos(15,"Dragón", listOf("Hielo", "Dragón", "Hada"), listOf("Dragón")),
            PokemonTipos(16,"Siniestro", listOf("Lucha", "Bicho", "Hada"), listOf("Psíquico", "Fantasma")),
            PokemonTipos(17,"Acero", listOf("Fuego", "Lucha", "Tierra"), listOf("Hielo", "Roca", "Hada")),
            PokemonTipos(18,"Hada", listOf("Veneno", "Acero"), listOf("Lucha", "Dragón", "Siniestro"))
        )
    }
}