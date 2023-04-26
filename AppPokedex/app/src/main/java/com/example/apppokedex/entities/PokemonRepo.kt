package com.example.apppokedex.entities

class PokemonRepo {
    var pokemon = mutableListOf<Pokemons>()

    private var urlImagen : String = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"

    init {
        pokemon.add(Pokemons( 1, "Bulbasaur", "Planta/Veneno","Fuego/Psiquico/Volador/Hielo", urlImagen+"001.png", "Este Pokémon nace con una semilla en el lomo, que brota con el paso del tiempo.", "0,7 m", "6,9 Kg","Semilla", "Espesura", 1,  listOf(1,2,3)))
        pokemon.add(Pokemons( 2, "Ivysaur", "Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"002.png","Cuando le crece bastante el bulbo del lomo, pierde la capacidad de erguirse sobre las patas traseras.", "1,0 m", "13,0 Kg","Semilla","Espesura", 1, listOf(1,2,3)))
        pokemon.add(Pokemons( 3, "Venusaur", "Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"003.png","La planta florece cuando absorbe energía solar, lo cual le obliga a buscar siempre la luz del sol.","2,0 m", "100,0 Kg","Semilla","Espesura", 1, listOf(1,2,3)))

        pokemon.add(Pokemons( 4, "Charmander", "Fuego","Agua/Tierra/Roca",urlImagen+"004.png","Prefiere las cosas calientes. Dicen que cuando llueve le sale vapor de la punta de la cola.", "0,6 m", "8,5 Kg","Lagartija","Mar Llamas", 1, listOf(4,5,6)))
        pokemon.add(Pokemons( 5, "Charmeleon", "Fuego","Agua/Tierra/Roca",urlImagen+"005.png", "Este Pokémon de naturaleza agresiva ataca en combate con su cola llameante y hace trizas al rival con sus afiladas garras.","1,1 m", "19,0 Kg","Llama","Mar Llamas", 1, listOf(4,5,6)))
        pokemon.add(Pokemons( 6, "Charizard", "Fuego/Volador","Agua/Electrico/Roca",urlImagen+"006.png","Escupe un fuego tan caliente que funde las rocas. Causa incendios forestales sin querer.", "1,7 m", "90,5 Kg","Llama","Mar Llamas", 1,listOf(4,5,6)))

        pokemon.add(Pokemons( 7, "Squirtle", "Agua","Planta/Electrico",urlImagen+"007.png","Cuando retrae su largo cuello en el caparazón, dispara agua a una presión increíble.", "0,5 m", "9,0 Kg","Tortuguita","Torrente", 1 , listOf(7,8,9)))
        pokemon.add(Pokemons( 8, "Wartortle", "Agua","Planta/Electrico",urlImagen+"008.png","Se lo considera un símbolo de longevidad. Los ejemplares más ancianos tienen musgo sobre el caparazón.", "1,0 m", "22,5 Kg","Tortugua","Torrente", 1, listOf(7,8,9)))
        pokemon.add(Pokemons( 9, "Blastoise", "Agua","Planta/Electrico",urlImagen+"009.png","Para acabar con su enemigo, lo aplasta con el peso de su cuerpo. En momentos de apuro, se esconde en el caparazón.", "1,6 m", "85,5 Kg","Armazón","Torrente", 1, listOf(7,8,9)))

        pokemon.add(Pokemons( 10, "Caterpie", "Bicho","Fuego/Psiquico/Volador/Hielo",urlImagen+"010.png","Para protegerse, despide un hedor horrible por las antenas con el que repele a sus enemigos.", "0.7 m", "6.9 Kg","Gusano","Polvo Escudo", 1, listOf(10,11,12)))
        pokemon.add(Pokemons( 11, "Metapod", "Bicho","Fuego/Psiquico/Volador/Hielo",urlImagen+"011.png","Como en este estado solo puede endurecer su coraza, permanece inmóvil a la espera de evolucionar.", "0.7 m", "6.9 Kg","Capullo","Mudar", 1, listOf(10,11,12)))
        pokemon.add(Pokemons( 12, "Butterfree", "Bicho/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"012.png","Aletea a gran velocidad para lanzar al aire sus escamas extremadamente tóxicas.", "0.7 m", "6.9 Kg","Mariposa","Ojo Compuesto", 1, listOf(10,11,12)))

        pokemon.add(Pokemons( 13, "Weedle", "Bicho/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"013.png","El aguijón de la cabeza es muy puntiagudo. Se alimenta de hojas oculto en la espesura de bosques y praderas.", "0.7 m", "6.9 Kg","Oruga","Polvo Escudo", 1, listOf(13,14,15)))
        pokemon.add(Pokemons( 14, "Kakuna", "Bicho/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"014.png","Aunque es casi incapaz de moverse, en caso de sentirse amenazado puede envenenar a los enemigos con su aguijón.", "0.7 m", "6.9 Kg","Capullo","Mudar", 1, listOf(13,14,15)))
        pokemon.add(Pokemons( 15, "Beedrill", "Bicho/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"015.png","Tiene tres aguijones venenosos, dos en las patas anteriores y uno en la parte baja del abdomen, con los que ataca a sus enemigos una y otra vez.", "0.7 m", "6.9 Kg","Abeja Veneno","Enjambre", 1, listOf(13,14,15)))

        pokemon.add(Pokemons( 16, "Pidgey", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"016.png","Su docilidad es tal que suelen defenderse levantando arena en lugar de contraatacar.", "0.7 m", "6.9 Kg","Pajarito","Vista Lince\n"+"Tumbos", 1, listOf(16,17,18)))
        pokemon.add(Pokemons( 17, "Pidgeotto", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"017.png","Su extraordinaria vitalidad y resistencia le permiten cubrir grandes distancias del territorio que habita en busca de presas.", "0.7 m", "6.9 Kg","Pájaro","Vista Lince\n"+"Tumbos", 1, listOf(16,17,18)))
        pokemon.add(Pokemons( 18, "Pidgeot", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"018.png","Este Pokémon vuela a una velocidad de 2 mach en busca de presas. Sus grandes garras son armas muy peligrosas.", "0.7 m", "6.9 Kg","Pájaro","Vista Lince\n"+"Tumbos", 1, listOf(16,17,18)))

        pokemon.add(Pokemons( 19, "Rattata", "Normal","Fuego/Psiquico/Volador/Hielo",urlImagen+"019.png","Es propenso a hincar los incisivos en cualquier cosa que se le ponga por delante. Si se ve alguno, seguramente haya cuarenta cerca.", "0.7 m", "6.9 Kg","Ratón","Fuga\n"+"Agallas", 1, listOf(19,20)))
        pokemon.add(Pokemons( 20, "Raticate", "Normal","Fuego/Psiquico/Volador/Hielo",urlImagen+"020.png","Gracias a las pequeñas membranas de las patas traseras, puede nadar por los ríos para capturar presas.", "0.7 m", "6.9 Kg","Ratón","Fuga\n"+"Agallas", 1, listOf(19,20)))

        pokemon.add(Pokemons( 21, "Spearow", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"021.png","A la hora de proteger su territorio, compensa su incapacidad para volar a gran altura con una increíble velocidad.", "0.7 m", "6.9 Kg","Pajarito","Vista Lince", 1, listOf(21,22)))
        pokemon.add(Pokemons( 22, "Fearow", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"022.png","Este Pokémon ha existido desde tiempos remotos. Al menor atisbo de peligro, alza el vuelo y huye.", "0.7 m", "6.9 Kg","Pico","Vista Lince", 1, listOf(21,22)))

        pokemon.add(Pokemons( 23, "Ekans", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"023.png","La longitud de este Pokémon aumenta con el tiempo. Por la noche, se enrosca en las ramas de los árboles para descansar.", "0.7 m", "6.9 Kg","Serpiente","Mudar\n"+"Intimidación", 1, listOf(23,24)))
        pokemon.add(Pokemons( 24, "Arbok", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"024.png","Se han llegado a identificar hasta seis variaciones distintas de los espeluznantes dibujos de su piel.", "0.7 m", "6.9 Kg","Cobra","Mudar\n"+"Intimidación", 1, listOf(23,24)))

        pokemon.add(Pokemons( 25, "Pikachu", "Electrico","Fuego/Psiquico/Volador/Hielo",urlImagen+"025.png","Cuando se enfada, este Pokémon descarga la energía que almacena en el interior de las bolsas de las mejillas.", "0.7 m", "6.9 Kg","Ratón","Elec. Estática", 1, listOf(25,26)))
        pokemon.add(Pokemons( 26, "Raichu", "Electrico","Fuego/Psiquico/Volador/Hielo",urlImagen+"026.png","Su cola actúa como toma de tierra y descarga electricidad al suelo, lo que le protege de los calambrazos.", "0.7 m", "6.9 Kg","Ratón","Elec. Estática", 1, listOf(25,26)))

        pokemon.add(Pokemons( 27, "Sandshrew", "Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"027.png","Le gusta revolcarse por la arena seca para eliminar todo rastro de suciedad y humedad en la piel.", "0.7 m", "6.9 Kg","Ratón","Velo Arena", 1, listOf(27,28)))
        pokemon.add(Pokemons( 28, "Sandslash", "Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"028.png","Cuanto más seco es el terreno en el que habita, más duras y lisas se vuelven las púas que le recubren la espalda.", "0.7 m", "6.9 Kg","Ratón","Velo Arena", 1, listOf(27,28)))

        pokemon.add(Pokemons( 29, "Nidoran ♀", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"029.png","Posee un olfato más fino que los Nidoran♂. Usa los bigotes para percibir la dirección del viento y buscar comida a sotavento de sus depredadores.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1, listOf(29,30,31)))
        pokemon.add(Pokemons( 30, "Nidorina", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"030.png","Se cree que el cuerno de la frente se le ha atrofiado para evitar herir a sus crías al alimentarlas.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1, listOf(29,30,31)))
        pokemon.add(Pokemons( 31, "Nidoqueen", "Veneno/Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"031.png","Su defensa destaca sobre la capacidad ofensiva. Usa las escamas del cuerpo como una coraza para proteger a su prole de cualquier ataque.", "0.7 m", "6.9 Kg","Taladro","Punto Tóxico\n"+"Rivalidad", 1, listOf(29,30,31)))

        pokemon.add(Pokemons( 32, "Nidoran ♂", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"032.png","Mantiene sus grandes orejas levantadas, siempre alerta. Si advierte peligro, ataca inoculando una potente toxina con su cuerno frontal.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1, listOf(32,33,34)))
        pokemon.add(Pokemons( 33, "Nidorino", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"033.png","Dondequiera que va, parte rocas con su cuerno, más duro que un diamante, en busca de una Piedra Lunar.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1, listOf(32,33,34)))
        pokemon.add(Pokemons( 34, "Nidoking", "Veneno/Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"034.png","Una vez que se desboca, no hay quien lo pare. Solo se calma ante Nidoqueen, su compañera de toda la vida.", "0.7 m", "6.9 Kg","Taladro","Punto Tóxico\n"+"Rivalidad", 1, listOf(32,33,34)))


        pokemon.add(Pokemons( 150, "Mewtwo", "Písquico","Fantasma/Siniestro/Bicho",urlImagen+"150.png","Su ADN es casi el mismo que el de Mew. Sin embargo, su tamaño y carácter son muy diferentes.", "2,0 m", "122,0 Kg","Genetico","Presión", 1, listOf(150)))
        pokemon.add(Pokemons( 151, "Mew", "Písquico","Fantasma/Siniestro/Bicho",urlImagen+"151.png","Si se observa a través de un microscopio, puede distinguirse cuán corto, fino y delicado es el pelaje de este Pokémon.", "0,4 m", "4,0 Kg","Nueva Especie","Sincronía", 1, listOf(151)))
    }
}