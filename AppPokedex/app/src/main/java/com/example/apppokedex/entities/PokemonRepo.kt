package com.example.apppokedex.entities

class PokemonRepo {
    var pokemon = mutableListOf<Pokemons>()

    private var urlImagen : String = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"

    init {
        //pokemon.add(Pokemons( 1, "Bulbasaur", listOf("Planta","Veneno"),"Fuego/Psiquico/Volador/Hielo", urlImagen+"001.png", "Este Pokémon nace con una semilla en el lomo, que brota con el paso del tiempo.", "0,7 m", "6,9 Kg","Semilla", "Espesura", 1,  listOf(1,2,3)))
        //pokemon.add(Pokemons( 1, "Bulbasaur", listOf(1,7),"Fuego/Psiquico/Volador/Hielo", urlImagen+"001.png", "Este Pokémon nace con una semilla en el lomo, que brota con el paso del tiempo.", "0,7 m", "6,9 Kg","Semilla", "Espesura", 1,  listOf(1,2,3)))
        pokemon.add(Pokemons( 1, "Bulbasaur",  "Planta/Veneno","Fuego/Psiquico/Volador/Hielo", urlImagen+"001.png", "Este Pokémon nace con una semilla en el lomo, que brota con el paso del tiempo.", "0,7 m", "6,9 Kg","Semilla", "Espesura", 1, 0, 2))
        pokemon.add(Pokemons( 2, "Ivysaur", "Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"002.png","Cuando le crece bastante el bulbo del lomo, pierde la capacidad de erguirse sobre las patas traseras.", "1,0 m", "13,0 Kg","Semilla","Espesura", 1,1,3))
        pokemon.add(Pokemons( 3, "Venusaur", "Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"003.png","La planta florece cuando absorbe energía solar, lo cual le obliga a buscar siempre la luz del sol.","2,0 m", "100,0 Kg","Semilla","Espesura", 1, 2, 0))

        pokemon.add(Pokemons( 4, "Charmander", "Fuego","Agua/Tierra/Roca",urlImagen+"004.png","Prefiere las cosas calientes. Dicen que cuando llueve le sale vapor de la punta de la cola.", "0,6 m", "8,5 Kg","Lagartija","Mar Llamas", 1,0,5))
        pokemon.add(Pokemons( 5, "Charmeleon", "Fuego","Agua/Tierra/Roca",urlImagen+"005.png", "Este Pokémon de naturaleza agresiva ataca en combate con su cola llameante y hace trizas al rival con sus afiladas garras.","1,1 m", "19,0 Kg","Llama","Mar Llamas", 1,4,6))
        pokemon.add(Pokemons( 6, "Charizard", "Fuego/Volador","Agua/Electrico/Roca",urlImagen+"006.png","Escupe un fuego tan caliente que funde las rocas. Causa incendios forestales sin querer.", "1,7 m", "90,5 Kg","Llama","Mar Llamas", 1,5,0))

        pokemon.add(Pokemons( 7, "Squirtle", "Agua","Planta/Electrico",urlImagen+"007.png","Cuando retrae su largo cuello en el caparazón, dispara agua a una presión increíble.", "0,5 m", "9,0 Kg","Tortuguita","Torrente", 1 ,0,8))
        pokemon.add(Pokemons( 8, "Wartortle", "Agua","Planta/Electrico",urlImagen+"008.png","Se lo considera un símbolo de longevidad. Los ejemplares más ancianos tienen musgo sobre el caparazón.", "1,0 m", "22,5 Kg","Tortugua","Torrente", 1,7,9))
        pokemon.add(Pokemons( 9, "Blastoise", "Agua","Planta/Electrico",urlImagen+"009.png","Para acabar con su enemigo, lo aplasta con el peso de su cuerpo. En momentos de apuro, se esconde en el caparazón.", "1,6 m", "85,5 Kg","Armazón","Torrente", 1,9,0))

        pokemon.add(Pokemons( 10, "Caterpie", "Bicho","Fuego/Psiquico/Volador/Hielo",urlImagen+"010.png","Para protegerse, despide un hedor horrible por las antenas con el que repele a sus enemigos.", "0.7 m", "6.9 Kg","Gusano","Polvo Escudo", 1,0,11))
        pokemon.add(Pokemons( 11, "Metapod", "Bicho","Fuego/Psiquico/Volador/Hielo",urlImagen+"011.png","Como en este estado solo puede endurecer su coraza, permanece inmóvil a la espera de evolucionar.", "0.7 m", "6.9 Kg","Capullo","Mudar", 1,10,12))
        pokemon.add(Pokemons( 12, "Butterfree", "Bicho/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"012.png","Aletea a gran velocidad para lanzar al aire sus escamas extremadamente tóxicas.", "0.7 m", "6.9 Kg","Mariposa","Ojo Compuesto", 1,11,0))

        pokemon.add(Pokemons( 13, "Weedle", "Bicho/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"013.png","El aguijón de la cabeza es muy puntiagudo. Se alimenta de hojas oculto en la espesura de bosques y praderas.", "0.7 m", "6.9 Kg","Oruga","Polvo Escudo", 1,0,14))
        pokemon.add(Pokemons( 14, "Kakuna", "Bicho/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"014.png","Aunque es casi incapaz de moverse, en caso de sentirse amenazado puede envenenar a los enemigos con su aguijón.", "0.7 m", "6.9 Kg","Capullo","Mudar", 1,13,15))
        pokemon.add(Pokemons( 15, "Beedrill", "Bicho/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"015.png","Tiene tres aguijones venenosos, dos en las patas anteriores y uno en la parte baja del abdomen, con los que ataca a sus enemigos una y otra vez.", "0.7 m", "6.9 Kg","Abeja Veneno","Enjambre", 1,14,0))

        pokemon.add(Pokemons( 16, "Pidgey", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"016.png","Su docilidad es tal que suelen defenderse levantando arena en lugar de contraatacar.", "0.7 m", "6.9 Kg","Pajarito","Vista Lince\n"+"Tumbos", 1,0,17))
        pokemon.add(Pokemons( 17, "Pidgeotto", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"017.png","Su extraordinaria vitalidad y resistencia le permiten cubrir grandes distancias del territorio que habita en busca de presas.", "0.7 m", "6.9 Kg","Pájaro","Vista Lince\n"+"Tumbos", 1,16,18))
        pokemon.add(Pokemons( 18, "Pidgeot", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"018.png","Este Pokémon vuela a una velocidad de 2 mach en busca de presas. Sus grandes garras son armas muy peligrosas.", "0.7 m", "6.9 Kg","Pájaro","Vista Lince\n"+"Tumbos", 1,17,0))

        pokemon.add(Pokemons( 19, "Rattata", "Normal","Fuego/Psiquico/Volador/Hielo",urlImagen+"019.png","Es propenso a hincar los incisivos en cualquier cosa que se le ponga por delante. Si se ve alguno, seguramente haya cuarenta cerca.", "0.7 m", "6.9 Kg","Ratón","Fuga\n"+"Agallas", 1,0,20))
        pokemon.add(Pokemons( 20, "Raticate", "Normal","Fuego/Psiquico/Volador/Hielo",urlImagen+"020.png","Gracias a las pequeñas membranas de las patas traseras, puede nadar por los ríos para capturar presas.", "0.7 m", "6.9 Kg","Ratón","Fuga\n"+"Agallas", 1,19,0))

        pokemon.add(Pokemons( 21, "Spearow", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"021.png","A la hora de proteger su territorio, compensa su incapacidad para volar a gran altura con una increíble velocidad.", "0.7 m", "6.9 Kg","Pajarito","Vista Lince", 1,0,22))
        pokemon.add(Pokemons( 22, "Fearow", "Normal/Volador","Fuego/Psiquico/Volador/Hielo",urlImagen+"022.png","Este Pokémon ha existido desde tiempos remotos. Al menor atisbo de peligro, alza el vuelo y huye.", "0.7 m", "6.9 Kg","Pico","Vista Lince", 1,21,0))

        pokemon.add(Pokemons( 23, "Ekans", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"023.png","La longitud de este Pokémon aumenta con el tiempo. Por la noche, se enrosca en las ramas de los árboles para descansar.", "0.7 m", "6.9 Kg","Serpiente","Mudar\n"+"Intimidación", 1,0,24))
        pokemon.add(Pokemons( 24, "Arbok", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"024.png","Se han llegado a identificar hasta seis variaciones distintas de los espeluznantes dibujos de su piel.", "0.7 m", "6.9 Kg","Cobra","Mudar\n"+"Intimidación", 1,23,0))

        pokemon.add(Pokemons( 25, "Pikachu", "Electrico","Fuego/Psiquico/Volador/Hielo",urlImagen+"025.png","Cuando se enfada, este Pokémon descarga la energía que almacena en el interior de las bolsas de las mejillas.", "0.7 m", "6.9 Kg","Ratón","Elec. Estática", 1,0,26))
        pokemon.add(Pokemons( 26, "Raichu", "Electrico","Fuego/Psiquico/Volador/Hielo",urlImagen+"026.png","Su cola actúa como toma de tierra y descarga electricidad al suelo, lo que le protege de los calambrazos.", "0.7 m", "6.9 Kg","Ratón","Elec. Estática", 1,25,0))

        pokemon.add(Pokemons( 27, "Sandshrew", "Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"027.png","Le gusta revolcarse por la arena seca para eliminar todo rastro de suciedad y humedad en la piel.", "0.7 m", "6.9 Kg","Ratón","Velo Arena", 1,0,28))
        pokemon.add(Pokemons( 28, "Sandslash", "Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"028.png","Cuanto más seco es el terreno en el que habita, más duras y lisas se vuelven las púas que le recubren la espalda.", "0.7 m", "6.9 Kg","Ratón","Velo Arena", 1,27,0))

        pokemon.add(Pokemons( 29, "Nidoran ♀", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"029.png","Posee un olfato más fino que los Nidoran♂. Usa los bigotes para percibir la dirección del viento y buscar comida a sotavento de sus depredadores.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1,0,30))
        pokemon.add(Pokemons( 30, "Nidorina", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"030.png","Se cree que el cuerno de la frente se le ha atrofiado para evitar herir a sus crías al alimentarlas.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1,29,31))
        pokemon.add(Pokemons( 31, "Nidoqueen", "Veneno/Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"031.png","Su defensa destaca sobre la capacidad ofensiva. Usa las escamas del cuerpo como una coraza para proteger a su prole de cualquier ataque.", "0.7 m", "6.9 Kg","Taladro","Punto Tóxico\n"+"Rivalidad", 1,30,0))

        pokemon.add(Pokemons( 32, "Nidoran ♂", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"032.png","Mantiene sus grandes orejas levantadas, siempre alerta. Si advierte peligro, ataca inoculando una potente toxina con su cuerno frontal.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1,0,33))
        pokemon.add(Pokemons( 33, "Nidorino", "Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"033.png","Dondequiera que va, parte rocas con su cuerno, más duro que un diamante, en busca de una Piedra Lunar.", "0.7 m", "6.9 Kg","Pin Veneno","Punto Tóxico\n"+"Rivalidad", 1,32,34))
        pokemon.add(Pokemons( 34, "Nidoking", "Veneno/Tierra","Fuego/Psiquico/Volador/Hielo",urlImagen+"034.png","Una vez que se desboca, no hay quien lo pare. Solo se calma ante Nidoqueen, su compañera de toda la vida.", "0.7 m", "6.9 Kg","Taladro","Punto Tóxico\n"+"Rivalidad", 1,33,0))

        pokemon.add(Pokemons( 35, "Clefairy","Hada","Acero/Veneno",urlImagen+"035.png","", "", "","","\n"+"", 1,0,36))
        pokemon.add(Pokemons( 36, "Clefable","","",urlImagen+"036.png","", "", "","","\n"+"", 1,35,0))

        pokemon.add(Pokemons( 37, "Vulpix", "","",urlImagen+"037.png","", "", "","","\n"+"", 1,0,38))
        pokemon.add(Pokemons( 38, "Ninetales","","",urlImagen+"038.png","", "", "","","\n"+"", 1,37,0))

        pokemon.add(Pokemons( 39, "Jigglypuff","","",urlImagen+"039.png","", "", "","","\n"+"", 1,0,40))
        pokemon.add(Pokemons( 40, "Wigglytuff","","",urlImagen+"040.png","", "", "","","\n"+"", 1,39,0))

        pokemon.add(Pokemons( 41, "Zubat", "","",urlImagen+"041.png","", "", "","","\n"+"", 1,0,42))
        pokemon.add(Pokemons( 42, "Golbat","","",urlImagen+"042.png","", "", "","","\n"+"", 1,41,0))

        pokemon.add(Pokemons( 43, "Oddish","","",urlImagen+"043.png","", "", "","","\n"+"", 1,0,44))
        pokemon.add(Pokemons( 44, "Gloom","","",urlImagen+"044.png","", "", "","","\n"+"", 1,43,45))
        pokemon.add(Pokemons( 45, "Vileplume","","",urlImagen+"045.png","", "", "","","\n"+"", 1,44,0))

        pokemon.add(Pokemons( 46, "Paras", "","",urlImagen+"046.png","", "", "","","\n"+"", 1,0,47))
        pokemon.add(Pokemons( 47, "Parasect","","",urlImagen+"047.png","", "", "","","\n"+"", 1,46,0))

        pokemon.add(Pokemons( 48, "Venonat","","",urlImagen+"048.png","", "", "","","\n"+"", 1,0,49))
        pokemon.add(Pokemons( 49, "Venomoth","","",urlImagen+"049.png","", "", "","","\n"+"", 1,48,0))

        pokemon.add(Pokemons( 50, "Diglett","","",urlImagen+"050.png","", "", "","","\n"+"", 1,0,51))
        pokemon.add(Pokemons( 51, "Dugtrio","","",urlImagen+"051.png","", "", "","","\n"+"", 1,50,0))

        pokemon.add(Pokemons( 52, "Meowth","","",urlImagen+"052.png","", "", "","","\n"+"", 1,0,53))
        pokemon.add(Pokemons( 53, "Persian","","",urlImagen+"053.png","", "", "","","\n"+"", 1,52,0))

        pokemon.add(Pokemons( 54, "Psyduck","","",urlImagen+"054.png","", "", "","","\n"+"", 1,0,55))
        pokemon.add(Pokemons( 55, "Golduck","","",urlImagen+"055.png","", "", "","","\n"+"", 1,54,0))

        pokemon.add(Pokemons( 56, "Mankey","","",urlImagen+"056.png","", "", "","","\n"+"", 1,0,57))
        pokemon.add(Pokemons( 57, "Primeape","","",urlImagen+"057.png","", "", "","","\n"+"", 1,56,0))

        pokemon.add(Pokemons( 58, "Growlithe","","",urlImagen+"058.png","", "", "","","\n"+"", 1,0,59))
        pokemon.add(Pokemons( 59, "Arcanine","","",urlImagen+"059.png","", "", "","","\n"+"", 1,58,0))

        pokemon.add(Pokemons( 60, "Poliwag", "","",urlImagen+"060.png","", "", "","","\n"+"", 1,0,61))
        pokemon.add(Pokemons( 61, "Poliwhirl","","",urlImagen+"061.png","", "", "","","\n"+"", 1,60,62))
        pokemon.add(Pokemons( 62, "Poliwrath","","",urlImagen+"062.png","", "", "","","\n"+"", 1,61,0))

        pokemon.add(Pokemons( 63, "Abra","","",urlImagen+"063.png","", "", "","","\n"+"", 1,0,64))
        pokemon.add(Pokemons( 64, "Kadabra","","",urlImagen+"064.png","", "", "","","\n"+"", 1,63,65))
        pokemon.add(Pokemons( 65, "Alakazam","","",urlImagen+"065.png","", "", "","","\n"+"", 1,64,0))

        pokemon.add(Pokemons( 66, "Machop","","",urlImagen+"066.png","", "", "","","\n"+"", 1,0,67))
        pokemon.add(Pokemons( 67, "Machoke","","",urlImagen+"067.png","", "", "","","\n"+"", 1,66,68))
        pokemon.add(Pokemons( 68, "Machamp","","",urlImagen+"068.png","", "", "","","\n"+"", 1,67,0))

        pokemon.add(Pokemons( 69, "Bellsprout","","",urlImagen+"069.png","", "", "","","\n"+"", 1,0,70))
        pokemon.add(Pokemons( 70, "Weepinbell","","",urlImagen+"070.png","", "", "","","\n"+"", 1,69,71))
        pokemon.add(Pokemons( 71, "Victreebel","","",urlImagen+"071.png","", "", "","","\n"+"", 1,70,0))

        pokemon.add(Pokemons( 72, "Tentacool", "","",urlImagen+"072.png","", "", "","","\n"+"", 1,0,73))
        pokemon.add(Pokemons( 73, "Tentacruel", "","",urlImagen+"073.png","", "", "","","\n"+"", 1,72,0))

        pokemon.add(Pokemons( 74, "Geodude", "","",urlImagen+"074.png","", "", "","","\n"+"", 1,0,75))
        pokemon.add(Pokemons( 75, "Graveler", "","",urlImagen+"075.png","", "", "","","\n"+"", 1,74,76))
        pokemon.add(Pokemons( 76, "Golem","","",urlImagen+"076.png","", "", "","","\n"+"", 1,75,0))

        pokemon.add(Pokemons( 77, "Ponyta","","",urlImagen+"077.png","", "", "","","\n"+"", 1,0,78))
        pokemon.add(Pokemons( 78, "Rapidash","","",urlImagen+"078.png","", "", "","","\n"+"", 1,77,0))

        pokemon.add(Pokemons( 79, "Slowpoke", "","",urlImagen+"079.png","", "", "","","\n"+"", 1,0,80))
        pokemon.add(Pokemons( 80, "Slowbro", "","",urlImagen+"080.png","", "", "","","\n"+"", 1,79,0))

        pokemon.add(Pokemons( 81, "Magnemite", "","",urlImagen+"081.png","", "", "","","\n"+"", 1,0,82))
        pokemon.add(Pokemons( 82, "Magneton","","",urlImagen+"082.png","", "", "","","\n"+"", 1,81,0))

        pokemon.add(Pokemons( 83, "Farfetch’d", "","",urlImagen+"083.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 84, "Doduo","","",urlImagen+"084.png","", "", "","","\n"+"", 1,0,85))
        pokemon.add(Pokemons( 85, "Dodrio", "","",urlImagen+"085.png","", "", "","","\n"+"", 1,84,0))

        pokemon.add(Pokemons( 86, "Seel", "","",urlImagen+"086.png","", "", "","","\n"+"", 1,0,87))
        pokemon.add(Pokemons( 87, "Dewgong", "","",urlImagen+"087.png","", "", "","","\n"+"", 1,86,0))

        pokemon.add(Pokemons( 88, "Grimer", "","",urlImagen+"088.png","", "", "","","\n"+"", 1,0,89))
        pokemon.add(Pokemons( 89, "Muk","","",urlImagen+"089.png","", "", "","","\n"+"", 1,88,0))

        pokemon.add(Pokemons( 90, "Shellder","","",urlImagen+"090.png","", "", "","","\n"+"", 1,0,91))
        pokemon.add(Pokemons( 91, "Cloyster","","",urlImagen+"091.png","", "", "","","\n"+"", 1,90,0))

        pokemon.add(Pokemons( 92, "Gastly","","",urlImagen+"092.png","", "", "","","\n"+"", 1,0,93))
        pokemon.add(Pokemons( 93, "Haunter","","",urlImagen+"093.png","", "", "","","\n"+"", 1,92,94))
        pokemon.add(Pokemons( 94, "Gengar","","",urlImagen+"094.png","", "", "","","\n"+"", 1,93,0))

        pokemon.add(Pokemons( 95, "Onix","","",urlImagen+"095.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 96, "Drowzee", "","",urlImagen+"096.png","", "", "","","\n"+"", 1,0,97))
        pokemon.add(Pokemons( 97, "Hypno","","",urlImagen+"097.png","", "", "","","\n"+"", 1,96,0))

        pokemon.add(Pokemons( 98, "Krabby", "","",urlImagen+"098.png","", "", "","","\n"+"", 1,0,99))
        pokemon.add(Pokemons( 99, "Kingler","","",urlImagen+"099.png","", "", "","","\n"+"", 1,98,0))

        pokemon.add(Pokemons( 100, "Voltorb","","",urlImagen+"100.png","", "", "","","\n"+"", 1,0,101))
        pokemon.add(Pokemons( 101, "Electrode", "","",urlImagen+"101.png","", "", "","","\n"+"", 1,100,0))

        pokemon.add(Pokemons( 102, "Exeggcute", "","",urlImagen+"102.png","", "", "","","\n"+"", 1,0,103))
        pokemon.add(Pokemons( 103, "Exeggutor", "","",urlImagen+"103.png","", "", "","","\n"+"", 1,102,0))

        pokemon.add(Pokemons( 104, "Cubone", "","",urlImagen+"104.png","", "", "","","\n"+"", 1,0,105))
        pokemon.add(Pokemons( 105, "Marowak", "","",urlImagen+"105.png","", "", "","","\n"+"", 1,104,0))

        pokemon.add(Pokemons( 106, "Hitmonlee","","",urlImagen+"106.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 107, "Hitmonchan", "","",urlImagen+"107.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 108, "Lickitung","","",urlImagen+"108.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 109, "Koffing","","",urlImagen+"109.png","", "", "","","\n"+"", 1,0,110))
        pokemon.add(Pokemons( 110, "Weezing","","",urlImagen+"110.png","", "", "","","\n"+"", 1,109,0))

        pokemon.add(Pokemons( 111, "Rhyhorn","","",urlImagen+"111.png","", "", "","","\n"+"", 1,0,112))
        pokemon.add(Pokemons( 112, "Rhydon", "","",urlImagen+"112.png","", "", "","","\n"+"", 1,111,0))

        pokemon.add(Pokemons( 113, "Chansey","","",urlImagen+"113.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 114, "Tangela","","",urlImagen+"114.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 115, "Kangaskhan","","",urlImagen+"115.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 116, "Horsea","","",urlImagen+"116.png","", "", "","","\n"+"", 1,0,117))
        pokemon.add(Pokemons( 117, "Seadra", "","",urlImagen+"117.png","", "", "","","\n"+"", 1,116,0))

        pokemon.add(Pokemons( 118, "Goldeen", "","",urlImagen+"118.png","", "", "","","\n"+"", 1,0,119))
        pokemon.add(Pokemons( 119, "Seaking", "","",urlImagen+"119.png","", "", "","","\n"+"", 1,118,0))

        pokemon.add(Pokemons( 120, "Staryu","","",urlImagen+"120.png","", "", "","","\n"+"", 1,0,120))
        pokemon.add(Pokemons( 121, "Starmie", "","",urlImagen+"121.png","", "", "","","\n"+"", 1,120,0))

        pokemon.add(Pokemons( 122, "Mr. Mime", "","",urlImagen+"122.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 123, "Scyther", "","",urlImagen+"123.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 124, "Jynx","","",urlImagen+"124.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 125, "Electabuzz", "","",urlImagen+"125.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 126, "Magmar", "","",urlImagen+"126.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 127, "Pinsir", "","",urlImagen+"127.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 128, "Tauros","","",urlImagen+"128.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 129, "Magikarp", "","",urlImagen+"129.png","", "", "","","\n"+"", 1,0,130))
        pokemon.add(Pokemons( 130, "Gyarados","","",urlImagen+"130.png","", "", "","","\n"+"", 1,129,0))

        pokemon.add(Pokemons( 131, "Lapras","","",urlImagen+"131.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 132, "Ditto", "","",urlImagen+"132.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 133, "Eevee", "","",urlImagen+"133.png","", "", "","","\n"+"", 1,0, 133))         //FALTA VER COMO IMPLEMENTAR CUANDO TIENE MAS DE UNA EVOLUCION
        pokemon.add(Pokemons( 134, "Vaporeon", "","",urlImagen+"134.png","", "", "","","\n"+"", 1,133,0))
        pokemon.add(Pokemons( 135, "Jolteon", "","",urlImagen+"135.png","", "", "","","\n"+"", 1,133,0))
        pokemon.add(Pokemons( 136, "Flareon ", "","",urlImagen+"136.png","", "", "","","\n"+"", 1,133,0))

        pokemon.add(Pokemons( 137, "Porygon", "","",urlImagen+"137.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 138, "Omanyte", "","",urlImagen+"138.png","", "", "","","\n"+"", 1,0,139))
        pokemon.add(Pokemons( 139, "Omastar", "","",urlImagen+"139.png","", "", "","","\n"+"", 1,138,0, ))

        pokemon.add(Pokemons( 140, "Kabuto", "","",urlImagen+"140.png","", "", "","","\n"+"", 1,0,141))
        pokemon.add(Pokemons( 141, "Kabutops", "","",urlImagen+"141.png","", "", "","","\n"+"", 1,140,0,))

        pokemon.add(Pokemons( 142, "Aerodactyl", "","",urlImagen+"142.png","", "", "","","\n"+"", 1,0,0, ))

        pokemon.add(Pokemons( 143, "Snorlax", "","",urlImagen+"143.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 144, "Articuno", "","",urlImagen+"144.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 145, "Zapdos", "","",urlImagen+"145.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 146, "Moltres", "","",urlImagen+"146.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 147, "Dratini", "Dragon","",urlImagen+"147.png","", "", "","","\n"+"", 1,0,148))
        pokemon.add(Pokemons( 148, "Dragonair", "Dragon","",urlImagen+"148.png","", "", "","","\n"+"", 1,147,149))
        pokemon.add(Pokemons( 149, "Dragonite", "Dragon/Volador","",urlImagen+"149.png","", "", "","","\n"+"", 1,148,0))

        pokemon.add(Pokemons( 150, "Mewtwo", "Písquico","Fantasma/Siniestro/Bicho",urlImagen+"150.png","Su ADN es casi el mismo que el de Mew. Sin embargo, su tamaño y carácter son muy diferentes.", "2,0 m", "122,0 Kg","Genetico","Presión", 1,0,0))

        pokemon.add(Pokemons( 151, "Mew", "Písquico","Fantasma/Siniestro/Bicho",urlImagen+"151.png","Si se observa a través de un microscopio, puede distinguirse cuán corto, fino y delicado es el pelaje de este Pokémon.", "0,4 m", "4,0 Kg","Nueva Especie","Sincronía", 1,0,0))
    }
}