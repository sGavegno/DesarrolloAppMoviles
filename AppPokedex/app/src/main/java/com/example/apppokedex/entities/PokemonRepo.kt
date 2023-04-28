package com.example.apppokedex.entities

class PokemonRepo {
    var pokemon = mutableListOf<Pokemons>()

    private var urlImagen : String = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"

    init {
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

        pokemon.add(Pokemons( 35, "Clefairy","Hada","Acero/Veneno",urlImagen+"035.png","Se dice que la felicidad llegará a quien vea un grupo de Clefairy bailando a la luz de la luna llena.", "0,6 m", "7,5 Kg","Hada","Gran Encanto\n"+"Muro Magico", 1,0,36))
        pokemon.add(Pokemons( 36, "Clefable","Hada","Acero/Veneno",urlImagen+"036.png","Este Pokémon de aspecto feérico, raramente visto por los humanos, corre a esconderse en cuanto detecta que hay alguien cerca. ", "1,3 m", "40,0 Kg","Hada","Gran Encanto\n"+"Muro Magico", 1,35,0))

        pokemon.add(Pokemons( 37, "Vulpix", "Fuego","Agua/Tierra/Roca",urlImagen+"037.png","De pequeño, tiene seis colas de gran belleza. A medida que crece, le van saliendo más.", "0,6 m", "9,9 Kg","Zorro","Absorbe Fuego", 1,0,38))
        pokemon.add(Pokemons( 38, "Ninetales","Fuego","Agua/Tierra/Roca",urlImagen+"038.png","Cuentan que llega a vivir hasta mil años y que cada una de las colas posee poderes sobrenaturales.", "1,1 m", "19,9 Kg","Zorro","Absorbe Fuego", 1,37,0))

        pokemon.add(Pokemons( 39, "Jigglypuff","Normal/Hada","Acero/Veneno",urlImagen+"039.png","Cuando le tiemblan sus redondos y adorables ojos, entona una melodía agradable y misteriosa con la que duerme a sus enemigos. ", "0,5 m", "5,5 Kg","Globo","Gran Encanto\n"+"Tenacidad", 1,0,40))
        pokemon.add(Pokemons( 40, "Wigglytuff","Normal/Hada","Acero/Veneno",urlImagen+"040.png","Tiene un pelaje muy fino. Se recomienda no enfadarlo, o se inflará y golpeará con todo su cuerpo.", "1,0 m", "12,0 kg","Globo","Gran Encanto\n"+"Tenacidad", 1,39,0))

        pokemon.add(Pokemons( 41, "Zubat", "Veneno/Volador","Psiquico/Electrico/Hielo/Roca",urlImagen+"041.png","Emite ondas ultrasónicas por la boca para escrutar el entorno, lo que le permite volar con pericia por cuevas angostas.", "0,8 m", "7,5 kg","Murciélago","Fuerza Mental", 1,0,42))
        pokemon.add(Pokemons( 42, "Golbat","Veneno/Volador","Psiquico/Electrico/Hielo/Roca",urlImagen+"042.png","Le encanta chuparles la sangre a los seres vivos. En ocasiones comparte la preciada colecta con otros congéneres hambrientos.", "1,6 m", "55,0 kg","Murciélago","Fuerza Mental", 1,41,0))

        pokemon.add(Pokemons( 43, "Oddish","Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"043.png","Se mueve al exponerse a la luz de la luna. Merodea por la noche para esparcir sus semillas. ", "0,5 m", "5,4 kg","Hierbajo","Clorofila", 1,0,44))
        pokemon.add(Pokemons( 44, "Gloom","Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"044.png","Libera un fétido olor por los pistilos. El fuerte hedor hace perder el conocimiento a cualquiera que se encuentre en un radio de 2 km.", "0,8 m", "8,6 kg","Hierbajo","Clorofila", 1,43,45))
        pokemon.add(Pokemons( 45, "Vileplume","Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"045.png","Tiene los pétalos más grandes del mundo. Al caminar, de ellos se desprenden densas nubes de polen tóxico. ", "1,2 m", "18,6 Kg","Flor","Clorofila", 1,44,0))

        pokemon.add(Pokemons( 46, "Paras", "Bicho/Panta","Fuego/Volador/Hielo/Veneno/Roca/Bicho",urlImagen+"046.png","Escarba en el suelo para extraer nutrientes de las raíces de los árboles, que las setas del lomo absorben después casi por completo.", "0,3 m", "5,4 kg","Hongo","Efecto Espora\n"+"Piel Seca", 1,0,47))
        pokemon.add(Pokemons( 47, "Parasect","Bicho/Panta","Fuego/Volador/Hielo/Veneno/Roca/Bicho",urlImagen+"047.png","Tras largo tiempo absorbiendo la energía del huésped, la seta parásita del lomo es la que parece controlar la voluntad de este Pokémon. ", "1,0 m", "29,5 kg","Hongo","Efecto Espora\n"+"Piel Seca", 1,46,0))

        pokemon.add(Pokemons( 48, "Venonat","Bicho/Veneno","Fuego/Psiquico/Volador/Roca",urlImagen+"048.png","Rezuma veneno por todo su cuerpo. De noche, atrapa y come pequeños Pokémon insecto atraídos por la luz. ", "1,0 m", "30,0 Kg","Insecto","Ojo Compuesto\n"+"Cromolente", 1,0,49))
        pokemon.add(Pokemons( 49, "Venomoth","Bicho/Veneno","Fuego/Psiquico/Volador/Roca",urlImagen+"049.png","Tiene las alas cubiertas de escamas. Cada vez que las bate, esparce un polvillo sumamente venenoso", "1,5 m", "12,5 Kg","Polilla Venenosa","Polvo Escudo\n"+"Cromolente", 1,48,0))

        pokemon.add(Pokemons( 50, "Diglett","Tierra","Agua/Planta/Hielo",urlImagen+"050.png","Vive 1 m por debajo del suelo, donde se alimenta de raíces. A veces también aparece en la superficie.", "0,2 m", "0,8 Kg","Topo","Velo Arena\n"+"Trampa Arena", 1,0,51))
        pokemon.add(Pokemons( 51, "Dugtrio","Tierra","Agua/Planta/Hielo",urlImagen+"051.png","Sus tres cabezas suben y bajan para remover la tierra cercana y facilitar así la excavación. ", "0,7 m", "33,3 Kg","Topo","Velo Arena\n"+"Trampa Arena", 1,50,0))

        pokemon.add(Pokemons( 52, "Meowth","Normal","Lucha",urlImagen+"052.png","Durante el día, se dedica a dormir. De noche, vigila su territorio con un brillo en los ojos. ", "0,4 m", "4,2 Kg","Gato Araña","Recogida\n"+"Experto", 1,0,53))
        pokemon.add(Pokemons( 53, "Persian","Normal","Lucha",urlImagen+"053.png","Aunque es muy admirado por el pelaje, es difícil de entrenar como mascota porque enseguida suelta arañazos.", "1,0 m", "32,0 Kg","Gato Felino","Experto\n"+"Flexibilidad", 1,52,0))

        pokemon.add(Pokemons( 54, "Psyduck","Agua","Planta/Electrico",urlImagen+"054.png","Padece continuamente dolores de cabeza. Cuando son muy fuertes, empieza a usar misteriosos poderes. ", "0,8 m", "16,6 Kg","Pato","Humedad\n"+"Aclimatacion", 1,0,55))
        pokemon.add(Pokemons( 55, "Golduck","Agua","Planta/Electrico",urlImagen+"055.png","Cuando nada a toda velocidad usando sus largas extremidades palmeadas, su frente comienza a brillar.", "1,7 m", "76,6 Kg","Pato","Humedad\n"+"Aclimatacion", 1,54,0))

        pokemon.add(Pokemons( 56, "Mankey","Lucha","Psiquico/Volador/Hada",urlImagen+"056.png","Vive en grupos en las copas de los árboles. Si pierde de vista a su manada, se siente solo y se enfada.", "0,5 m", "28,0 Kg","Mono Cerdo","Espíritu Vital\n"+"Irascible", 1,0,57))
        pokemon.add(Pokemons( 57, "Primeape","Lucha","Psiquico/Volador/Hada",urlImagen+"057.png","Se pone furioso si nota que alguien lo está mirando. Persigue a cualquiera que establezca contacto visual con él. ", "1,0 m", "32,0 Kg","Mono Cerdo","Espíritu Vital\n"+"Irascible", 1,56,0))

        pokemon.add(Pokemons( 58, "Growlithe","Fuego","Agua/Tierra/Roca",urlImagen+"058.png","De naturaleza valiente y honrada, se enfrenta sin miedo a enemigos más grandes y fuertes.", "0,7 m", "19,0 Kg","Perrito","Intimidacion\n"+"Absorbe Fuego", 1,0,59))
        pokemon.add(Pokemons( 59, "Arcanine","Fuego","Agua/Tierra/Roca",urlImagen+"059.png","Cuenta un antiguo pergamino que la gente se quedaba fascinada al verlo correr por las praderas.", "1,9 m", "155,0 Kg","Leyenda","Intimidacion\n"+"Absorbe Fuego", 1,58,0))

        pokemon.add(Pokemons( 60, "Poliwag", "Agua","Planta/Electrico",urlImagen+"060.png","Es más ágil en el agua que en la tierra. La espiral de su vientre no es más que parte de sus vísceras que se ven a través de la piel. ", "0,6 m", "12,4 Kg","Renacuajo","Humedad\n"+"Absorbe Agua", 1,0,61))
        pokemon.add(Pokemons( 61, "Poliwhirl","Agua","Planta/Electrico",urlImagen+"061.png","Mirar fijamente la espiral de su vientre provoca somnolencia, por lo que puede usarse como alternativa a las nanas para dormir a los niños.", "1,0 m", "20,0 kg","Renacuajo","Humedad\n"+"Absorbe Agua", 1,60,62))
        pokemon.add(Pokemons( 62, "Poliwrath","Agua/Lucha","Hada/Planta/Volador/Psiquico/Electrico",urlImagen+"062.png","Su cuerpo es puro músculo. Logra abrirse paso por aguas gélidas partiendo el hielo con sus fornidos brazos. ", "1,3 m", "54,0 Kg","Renacuajo","Humedad\n"+"Absorbe Agua", 1,61,0))

        pokemon.add(Pokemons( 63, "Abra","Psiquico","Fantasmo/Siniestro/Bicho",urlImagen+"063.png","Es capaz de usar sus poderes psíquicos aun estando dormido. Al parecer, el contenido del sueño influye en sus facultades.", "0,9 m", "19,5 Kg","Psi","Fuerza Mental\n"+"Sincronia", 1,0,64))
        pokemon.add(Pokemons( 64, "Kadabra","Psiquico","Fantasmo/Siniestro/Bicho",urlImagen+"064.png","Duerme suspendido en el aire gracias a sus poderes psíquicos. La cola, de una flexibilidad extraordinaria, hace las veces de almohada.", "1,3 m", "56,5 Kg","Psi","Fuerza Mental\n"+"Sincronia", 1,63,65))
        pokemon.add(Pokemons( 65, "Alakazam","Psiquico","Fantasmo/Siniestro/Bicho",urlImagen+"065.png","Posee una capacidad intelectual fuera de lo común que le permite recordar todo lo sucedido desde el instante de su nacimiento.", "1,5 m", "48,0 Kg","Psi","Fuerza Mental\n"+"Sincronia", 1,64,0))

        pokemon.add(Pokemons( 66, "Machop","Lucha","Psiquico/Volador/Hada",urlImagen+"066.png","Es una masa de músculos y, pese a su pequeño tamaño, tiene fuerza de sobra para levantar en brazos a 100 personas.", "0,8 m", "19,5 kg","Superpoder","Agallas\n"+"Indefenso", 1,0,67))
        pokemon.add(Pokemons( 67, "Machoke","Lucha","Psiquico/Volador/Hada",urlImagen+"067.png","Su musculoso cuerpo es tan fuerte que usa un cinto antifuerza para controlar sus movimientos.", "1,5 m", "70,5 Kg","Superpoder","Agallas\n"+"Indefenso", 1,66,68))
        pokemon.add(Pokemons( 68, "Machamp","Lucha","Psiquico/Volador/Hada",urlImagen+"068.png","Mueve rápidamente sus cuatro brazos para asestar incesantes golpes y puñetazos desde todos los ángulos.", "1,6 m", "130,0 kg","Superpoder","Agallas\n"+"Indefenso", 1,67,0))

        pokemon.add(Pokemons( 69, "Bellsprout","Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"069.png","Prefiere lugares cálidos y húmedos. Atrapa pequeños Pokémon insectos con sus lianas para devorarlos", "0,7 m", "4,0 Kg","Flor","Clorofila", 1,0,70))
        pokemon.add(Pokemons( 70, "Weepinbell","Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"070.png","Cuando tiene hambre, engulle a todo lo que se mueve. La pobre presa acaba disuelta en sus ácidos.", "1,0 m", "6,4 kg","Matamoscas","Clorofila", 1,69,71))
        pokemon.add(Pokemons( 71, "Victreebel","Planta/Veneno","Fuego/Psiquico/Volador/Hielo",urlImagen+"071.png","Atrae a su presa con un dulce aroma a miel. Una vez atrapada en la boca, la disuelve en tan solo un día, huesos incluidos.", "1,7 m", "15,5 Kg","Matamoscas","Clorofila", 1,70,0))

        pokemon.add(Pokemons( 72, "Tentacool", "Agua/Veneno","Psiquico/Electrico/Tierra",urlImagen+"072.png","Sus facultades natatorias son más bien escasas, por lo que se limita a flotar a la deriva en aguas poco profundas en busca de alimento.", "0,9 m", "45,5 Kg","Medusa","Cuerpo Puro\n"+"Viscosecrecion", 1,0,73))
        pokemon.add(Pokemons( 73, "Tentacruel", "Agua/Veneno","Psiquico/Electrico/Tierra",urlImagen+"073.png","Si las esferas rojas que tiene a ambos lados de la cabeza brillan con intensidad, indica que está a punto de lanzar ondas ultrasónicas", "1,6 m", "55,0 Kg","Medusa","Cuerpo Puro\n"+"Viscosecrecion", 1,72,0))

        pokemon.add(Pokemons( 74, "Geodude", "Roca/Tierra","Acero/Lucha/Agua/Hielo/Planta/Tierra",urlImagen+"074.png","Se suele encontrar en senderos de montaña y sitios parecidos. Conviene andar con cuidado para no pisarlo sin querer y provocar su enfado.", "0,4 m", "20,0 Kg","Roca","Cabeza Roca\n"+"Robustez", 1,0,75))
        pokemon.add(Pokemons( 75, "Graveler", "Roca/Tierra","Acero/Lucha/Agua/Hielo/Planta/Tierra",urlImagen+"075.png","Se le suele ver rodando montaña abajo. No evita los obstáculos, sino que los arrolla.", "1,0 m", "105,0 kg","Roca","Cabeza Roca\n"+"Robustez", 1,74,76))
        pokemon.add(Pokemons( 76, "Golem","Roca/Tierra","Acero/Lucha/Agua/Hielo/Planta/Tierra",urlImagen+"076.png","Nada más mudar la piel, su cuerpo se vuelve blando y blanquecino, pero se endurece al poco tiempo de entrar en contacto con el aire.", "1,4 m", "300,0 Kg","Megatón","Cabeza Roca\n"+"Robustez", 1,75,0))

        pokemon.add(Pokemons( 77, "Ponyta","Fuego","Agua/Tierra/Roca",urlImagen+"077.png","Al nacer es un poco lento, pero va fortaleciendo las patas paulatinamente al disputar carreras con sus congéneres. ", "1,0 m", "30,0 Kg","Caballo Fuego","Fuga\n"+"Absorber Fuego", 1,0,78))
        pokemon.add(Pokemons( 78, "Rapidash","Fuego","Agua/Tierra/Roca",urlImagen+"078.png","Su ardiente crin ondea al viento mientras atraviesa extensas praderas a una velocidad de 240 km/h.", "1,7 m", "95,0 Kg","Caballo Fuego","Fuga\n"+"Absorber Fuego", 1,77,0))

        pokemon.add(Pokemons( 79, "Slowpoke", "Agua/Psiquico","Fantasma/Siniestro/Planta/Electrico/Bicho",urlImagen+"079.png","", "", "","","\n"+"", 1,0,80))
        pokemon.add(Pokemons( 80, "Slowbro", "Agua/Psiquico","Fantasma/Siniestro/Planta/Electrico/Bicho",urlImagen+"080.png","", "", "","","\n"+"", 1,79,0))

        pokemon.add(Pokemons( 81, "Magnemite", "Electrico/Acero","Fuego/Lucha/Tierra",urlImagen+"081.png","", "", "","","\n"+"", 1,0,82))
        pokemon.add(Pokemons( 82, "Magneton","Electrico/Acero","Fuego/Lucha/Tierra",urlImagen+"082.png","", "", "","","\n"+"", 1,81,0))

        pokemon.add(Pokemons( 83, "Farfetch’d", "Normal/Volador","Electrico/Hielo/Roca",urlImagen+"083.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 84, "Doduo","Normal/Volador","Electrico/Hielo/Roca",urlImagen+"084.png","", "", "","","\n"+"", 1,0,85))
        pokemon.add(Pokemons( 85, "Dodrio", "Normal/Volador","Electrico/Hielo/Roca",urlImagen+"085.png","", "", "","","\n"+"", 1,84,0))

        pokemon.add(Pokemons( 86, "Seel", "Agua","Planta/Electrico",urlImagen+"086.png","", "", "","","\n"+"", 1,0,87))
        pokemon.add(Pokemons( 87, "Dewgong", "Agua/Hielo","Planta/Electrico/Lucha/Roca",urlImagen+"087.png","", "", "","","\n"+"", 1,86,0))

        pokemon.add(Pokemons( 88, "Grimer", "Veneno","Psiquico/Tierra",urlImagen+"088.png","", "", "","","\n"+"", 1,0,89))
        pokemon.add(Pokemons( 89, "Muk","Veneno","Psiquico/Tierra",urlImagen+"089.png","", "", "","","\n"+"", 1,88,0))

        pokemon.add(Pokemons( 90, "Shellder","Agua","Planta/Electrico",urlImagen+"090.png","", "", "","","\n"+"", 1,0,91))
        pokemon.add(Pokemons( 91, "Cloyster","Agua/Hielo","Planta/Electrico/Lucha/Roca",urlImagen+"091.png","", "", "","","\n"+"", 1,90,0))

        pokemon.add(Pokemons( 92, "Gastly","Fantasma/Veneno","Fantasmo/Siniestro/Psiquico/Tierra",urlImagen+"092.png","", "", "","","\n"+"", 1,0,93))
        pokemon.add(Pokemons( 93, "Haunter","Fantasma/Veneno","Fantasmo/Siniestro/Psiquico/Tierra",urlImagen+"093.png","", "", "","","\n"+"", 1,92,94))
        pokemon.add(Pokemons( 94, "Gengar","Fantasma/Veneno","Fantasmo/Siniestro/Psiquico/Tierra",urlImagen+"094.png","", "", "","","\n"+"", 1,93,0))

        pokemon.add(Pokemons( 95, "Onix","Roca/Tierra","Acero/Lucha/Agua/Hielo/Planta/Tierra",urlImagen+"095.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 96, "Drowzee", "Psiquico","Fantasmo/Siniestro/Bicho",urlImagen+"096.png","", "", "","","\n"+"", 1,0,97))
        pokemon.add(Pokemons( 97, "Hypno","Psiquico","Fantasmo/Siniestro/Bicho",urlImagen+"097.png","", "", "","","\n"+"", 1,96,0))

        pokemon.add(Pokemons( 98, "Krabby", "Agua","Planta/Electrico",urlImagen+"098.png","", "", "","","\n"+"", 1,0,99))
        pokemon.add(Pokemons( 99, "Kingler","Agua","Planta/Electrico",urlImagen+"099.png","", "", "","","\n"+"", 1,98,0))

        pokemon.add(Pokemons( 100, "Voltorb","Electrico","Tierra",urlImagen+"100.png","", "", "","","\n"+"", 1,0,101))
        pokemon.add(Pokemons( 101, "Electrode", "Electrico","Tierra",urlImagen+"101.png","", "", "","","\n"+"", 1,100,0))

        pokemon.add(Pokemons( 102, "Exeggcute", "Planta/Psiquico","Fantasma/Fuego/Volador/Hielo/Siniestro/Veneno/Bicho",urlImagen+"102.png","", "", "","","\n"+"", 1,0,103))
        pokemon.add(Pokemons( 103, "Exeggutor", "Planta/Psiquico","Fantasma/Fuego/Volador/Hielo/Siniestro/Veneno/Bicho",urlImagen+"103.png","", "", "","","\n"+"", 1,102,0))

        pokemon.add(Pokemons( 104, "Cubone", "Tierra","Agua/Planta/Hielo",urlImagen+"104.png","", "", "","","\n"+"", 1,0,105))
        pokemon.add(Pokemons( 105, "Marowak", "Tierra","Agua/Planta/Hielo",urlImagen+"105.png","", "", "","","\n"+"", 1,104,0))

        pokemon.add(Pokemons( 106, "Hitmonlee","Lucha","Psiquico/Volador/Hada",urlImagen+"106.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 107, "Hitmonchan", "Lucha","Psiquico/Volador/Hada",urlImagen+"107.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 108, "Lickitung","Normal","Lucha",urlImagen+"108.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 109, "Koffing","Veneno","Psiquico/Tierra",urlImagen+"109.png","", "", "","","\n"+"", 1,0,110))
        pokemon.add(Pokemons( 110, "Weezing","Veneno","Psiquico/Tierra",urlImagen+"110.png","", "", "","","\n"+"", 1,109,0))

        pokemon.add(Pokemons( 111, "Rhyhorn","Tierra/Roca","Acero/Hielo/Agua/Lucha/Planta/Tierra",urlImagen+"111.png","", "", "","","\n"+"", 1,0,112))
        pokemon.add(Pokemons( 112, "Rhydon", "Tierra/Roca","Acero/Hielo/Agua/Lucha/Planta/Tierra",urlImagen+"112.png","", "", "","","\n"+"", 1,111,0))

        pokemon.add(Pokemons( 113, "Chansey","Normal","Lucha",urlImagen+"113.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 114, "Tangela","Planta","Fuego/Volador/Hielo/Veneno/Bicho",urlImagen+"114.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 115, "Kangaskhan","Normal","Lucha",urlImagen+"115.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 116, "Horsea","Agua","Planta/Electrico",urlImagen+"116.png","", "", "","","\n"+"", 1,0,117))
        pokemon.add(Pokemons( 117, "Seadra", "Agua","Planta/Electrico",urlImagen+"117.png","", "", "","","\n"+"", 1,116,0))

        pokemon.add(Pokemons( 118, "Goldeen", "Agua","Planta/Electrico",urlImagen+"118.png","", "", "","","\n"+"", 1,0,119))
        pokemon.add(Pokemons( 119, "Seaking", "Agua","Planta/Electrico",urlImagen+"119.png","", "", "","","\n"+"", 1,118,0))

        pokemon.add(Pokemons( 120, "Staryu","Agua","Planta/Electrico",urlImagen+"120.png","", "", "","","\n"+"", 1,0,120))
        pokemon.add(Pokemons( 121, "Starmie", "Agua/Psiquico","Fantasma/Siniestro/Planta/Electrico/Bicho",urlImagen+"121.png","", "", "","","\n"+"", 1,120,0))

        pokemon.add(Pokemons( 122, "Mr. Mime", "Psiquico/Hada","Fantasma/Acero/Veneno",urlImagen+"122.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 123, "Scyther", "Bicho/Volador","Fuego/Volador/Electrico/Hielo/Roca",urlImagen+"123.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 124, "Jynx","Hielo/Psiquico","Acero/Fantasma/Fuego/Siniestro/Roca/Bicho",urlImagen+"124.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 125, "Electabuzz", "Electrico","Tierra",urlImagen+"125.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 126, "Magmar", "Fuego","Agua/Tierra/Roca",urlImagen+"126.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 127, "Pinsir", "Bicho","Fuego/Volador/Roca",urlImagen+"127.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 128, "Tauros","Normal","Lucha",urlImagen+"128.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 129, "Magikarp", "Agua","Planta/Electrico",urlImagen+"129.png","", "", "","","\n"+"", 1,0,130))
        pokemon.add(Pokemons( 130, "Gyarados","Agua/Volador","Electrico/Roca",urlImagen+"130.png","", "", "","","\n"+"", 1,129,0))

        pokemon.add(Pokemons( 131, "Lapras","Agua/Hielo","Planta/Electrico/Fuego/Roca",urlImagen+"131.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 132, "Ditto", "Normal","Lucha",urlImagen+"132.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 133, "Eevee", "Normal","Lucha",urlImagen+"133.png","", "", "","","\n"+"", 1,0, 133))         //FALTA VER COMO IMPLEMENTAR CUANDO TIENE MAS DE UNA EVOLUCION
        pokemon.add(Pokemons( 134, "Vaporeon", "Agua","Planta/Electrico",urlImagen+"134.png","", "", "","","\n"+"", 1,133,0))
        pokemon.add(Pokemons( 135, "Jolteon", "Electrico","Tierra",urlImagen+"135.png","", "", "","","\n"+"", 1,133,0))
        pokemon.add(Pokemons( 136, "Flareon ", "Fuego","Agua/Tierra/Roca",urlImagen+"136.png","", "", "","","\n"+"", 1,133,0))

        pokemon.add(Pokemons( 137, "Porygon", "Normal","Lucha",urlImagen+"137.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 138, "Omanyte", "Roca/Agua","Planta/Electrico/Lucha/Tierra",urlImagen+"138.png","", "", "","","\n"+"", 1,0,139))
        pokemon.add(Pokemons( 139, "Omastar", "Roca/Agua","Planta/Electrico/Lucha/Tierra",urlImagen+"139.png","", "", "","","\n"+"", 1,138,0, ))

        pokemon.add(Pokemons( 140, "Kabuto", "Roca/Agua","Planta/Electrico/Lucha/Tierra",urlImagen+"140.png","", "", "","","\n"+"", 1,0,141))
        pokemon.add(Pokemons( 141, "Kabutops", "Roca/Agua","Planta/Electrico/Lucha/Tierra",urlImagen+"141.png","", "", "","","\n"+"", 1,140,0,))

        pokemon.add(Pokemons( 142, "Aerodactyl", "Roca/Volador","Acero/Agua/Electrico/Hielo/Roca",urlImagen+"142.png","", "", "","","\n"+"", 1,0,0, ))

        pokemon.add(Pokemons( 143, "Snorlax", "Normal","Lucha",urlImagen+"143.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 144, "Articuno", "Hielo/Volador","Acero/Fuego/Electrico/Roca",urlImagen+"144.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 145, "Zapdos", "Electrico/Volador","Hielo/Roca",urlImagen+"145.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 146, "Moltres", "Fuego/Volador","Agua/Electrico/Roca",urlImagen+"146.png","", "", "","","\n"+"", 1,0,0))

        pokemon.add(Pokemons( 147, "Dratini", "Dragon","Hada/Hielo/Dragon",urlImagen+"147.png","", "", "","","\n"+"", 1,0,148))
        pokemon.add(Pokemons( 148, "Dragonair", "Dragon","Hada/Hielo/Dragon",urlImagen+"148.png","", "", "","","\n"+"", 1,147,149))
        pokemon.add(Pokemons( 149, "Dragonite", "Dragon/Volador","Hada/Dragon/Hielo/Roca",urlImagen+"149.png","", "", "","","\n"+"", 1,148,0))

        pokemon.add(Pokemons( 150, "Mewtwo", "Písquico","Fantasma/Siniestro/Bicho",urlImagen+"150.png","Su ADN es casi el mismo que el de Mew. Sin embargo, su tamaño y carácter son muy diferentes.", "2,0 m", "122,0 Kg","Genetico","Presión", 1,0,0))

        pokemon.add(Pokemons( 151, "Mew", "Písquico","Fantasma/Siniestro/Bicho",urlImagen+"151.png","Si se observa a través de un microscopio, puede distinguirse cuán corto, fino y delicado es el pelaje de este Pokémon.", "0,4 m", "4,0 Kg","Nueva Especie","Sincronía", 1,0,0))
    }
}