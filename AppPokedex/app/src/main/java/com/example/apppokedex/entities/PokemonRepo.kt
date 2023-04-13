package com.example.apppokedex.entities

class PokemonRepo {
    var pokemon = mutableListOf<Pokemons>()

    init {
        //pokemon.add(Pokemons( 1, "Bulbasaur", "Planta/Veneno", "www.imagen.com"))
        pokemon.add(Pokemons( 1, "Bulbasaur", "Planta/Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/001.png", "Fuego/Psiquico/Volador/Hielo", "0,7 m", "6,9 Kg"))
        pokemon.add(Pokemons( 2, "Ivysaur", "Planta/Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/002.png", "Fuego/Psiquico/Volador/Hielo", "1,0 m", "13,0 Kg"))
        pokemon.add(Pokemons( 3, "Venusaur", "Planta/Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/003.png", "Fuego/Psiquico/Volador/Hielo", "2,0 m", "100,0 Kg"))
        pokemon.add(Pokemons( 4, "Charmander", "Fuego", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/004.png", "Agua/Tierra/Roca", "0,6 m", "8,5 Kg"))
        pokemon.add(Pokemons( 5, "Charmeleon", "Fuego", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/005.png", "Agua/Tierra/Roca", "1,1 m", "19,0 Kg"))
        pokemon.add(Pokemons( 6, "Charizard", "Fuego/Volador", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/006.png", "Agua/Electrico/Roca", "1,7 m", "90,5 Kg"))
        pokemon.add(Pokemons( 7, "Squirtle", "Agua", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/007.png", "Planta/Electrico", "0,5 m", "9,0 Kg"))
        pokemon.add(Pokemons( 8, "Wartortle", "Agua", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/008.png", "Planta/Electrico", "1,0 m", "22,5    Kg"))
        pokemon.add(Pokemons( 9, "Blastoise", "Agua", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/009.png", "Planta/Electrico", "1,6 m", "85,5 Kg"))

        pokemon.add(Pokemons( 10, "Caterpie", "Bicho", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/010.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 11, "Metapod", "Bicho", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/011.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 12, "Butterfree", "Bicho/Volador", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/012.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 13, "Weedle", "Bicho/Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/013.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 14, "Kakuna", "Bicho/Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/014.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 15, "Beedrill", "Bicho/Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/015.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 16, "Pidgey", "Normal/Volador", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/016.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 17, "Pidgeotto", "Normal/Volador", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/017.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 18, "Pidgeot", "Normal/Volador", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/018.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 19, "Rattata", "Normal", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/019.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 20, "Raticate", "Normal", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/020.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 21, "Spearow", "Normal/Volador", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/021.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 22, "Fearow", "Normal/Volador", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/022.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 23, "Ekans", "Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/023.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 24, "Arbok", "Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/024.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 25, "Pikachu", "Electrico", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/025.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 26, "Raichu", "Electrico", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/026.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 27, "Sandshrew", "Tierra", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/027.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 28, "Sandslash", "Tierra", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/028.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 29, "Nidoran ♀", "Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/029.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 30, "Nidorina", "Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/030.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 31, "Nidoqueen", "Veneno/Tierra", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/031.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 32, "Nidoran ♂", "Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/032.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 33, "Nidorino", "Veneno", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/033.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))
        pokemon.add(Pokemons( 34, "Nidoking", "Veneno/Tierra", "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/034.png", "Fuego/Psiquico/Volador/Hielo", "0.7 m", "6.9 Kg"))

    }
}