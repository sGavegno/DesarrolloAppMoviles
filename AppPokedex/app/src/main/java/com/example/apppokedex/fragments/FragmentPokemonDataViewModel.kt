package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.Estadisticas
import com.example.apppokedex.entities.Evoluciones
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonItems
import com.example.apppokedex.entities.PokemonItemsRepo
import com.example.apppokedex.entities.PokemonStats
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.UserPokedex
import com.example.apppokedex.entities.Usuario
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class FragmentPokemonDataViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()
    val stateEvolucion = SingleLiveEvent<State>()
    val statePokemon = SingleLiveEvent<State>()
    val stateRemove = SingleLiveEvent<State>()
    val stateLvl = SingleLiveEvent<State>()
    val stateItems = SingleLiveEvent<State>()

    val pokemonData = SingleLiveEvent<Pokemon>()
    val pokemonPcData = SingleLiveEvent<Pc>()
    val pokemonEvolucionA = SingleLiveEvent<Pokemon>()
    val items = SingleLiveEvent<PokemonItems>()
    val itemsList = SingleLiveEvent<PokemonItemsRepo>()

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)
        try {
            var pokemon: Pokemon?
            viewModelScope.launch(Dispatchers.IO) {
                pokemon = getPokemonFireBase(idPokemon)
                if (pokemon != null) {
                    pokemonData.postValue(pokemon!!)
                    statePokemon.postValue(State.SUCCESS)
                } else {
                    statePokemon.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("myFireBaseLogin", "A ver $e")
            statePokemon.postValue(State.FAILURE)
        }
    }

    fun remuvePokemonPc(id: Int){
        stateRemove.postValue(State.LOADING)
        try {
            var user: Usuario?
            viewModelScope.launch(Dispatchers.IO) {
                user = remuvePokemonPcFireBase(id)
                if (user != null) {
                    preferencesManager.saveUser(user!!)
                    stateRemove.postValue(State.SUCCESS)
                } else {
                    stateRemove.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("myFireBaseLogin", "A ver $e")
            stateRemove.postValue(State.FAILURE)
        }

    }

    fun upLevelPokemon(id: Int){
        stateLvl.postValue(State.LOADING)
        val user = preferencesManager.getUserLogin()
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val pokeAux = user.pc?.filter { item -> item.id == id }?.get(0)
                if (pokeAux != null){
                    pokeAux.nivel = pokeAux.nivel!! + 1
                    //Subir de nivel aument la felicidad
                    if (pokeAux.felicidad!! <= 99){
                        pokeAux.felicidad = pokeAux.felicidad!! + 5
                    }else if (pokeAux.felicidad!! <= 199){
                        pokeAux.felicidad = pokeAux.felicidad!! + 3
                    }else if (pokeAux.felicidad!! <= 255){
                        pokeAux.felicidad = pokeAux.felicidad!! + 2
                    }
                    pokeAux.stats = calcularEstadisticas(pokeAux)
                    val evolucionA = checkEvolucion(pokeAux)
                    val result = updateUserFireBase(user)
                    if (result != null) {
                        //pokemonPcData.postValue(pokeAux!!)
                        preferencesManager.savePcPokemon(pokeAux)
                        if (evolucionA != null){
                            pokemonEvolucionA.postValue(evolucionA!!)
                        }
                        stateLvl.postValue(State.SUCCESS)
                    } else {
                        stateLvl.postValue(State.FAILURE)
                    }
                }else{
                    stateLvl.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception){
            Log.d("upLevelPokemon", "Error upLevelPokemon ")
            stateLvl.postValue(State.FAILURE)
        }
    }

    fun evolucionPokemon(pokemon: Pc, poke:Pokemon){
        stateEvolucion.postValue(State.LOADING)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val evolucionList = getPokemonEvolucionFireBase(poke.detalle!!.idCadenaEvolutiva!!)?.cadenaEvolutiva
                val evolucionA = evolucionList?.filter { item -> item.id == poke.id!! }?.get(0)

                val evolucionDe = getPokemonFireBase(pokemon.idPokemon!!)
                if (evolucionDe != null){
                    val user = preferencesManager.getUserLogin()
                    val pokemonPc = user.pc?.filter { item -> item.id == pokemon.id!! }?.get(0)
                    if (pokemonPc != null) {
                        pokemonPc.idPokemon = poke.id
                        pokemonPc.nombre = poke.nombre
                        if (pokemonPc.mote == evolucionDe.nombre) {
                            pokemonPc.mote = poke.nombre
                        }
                        pokemonPc.tipo = poke.tipo
                        pokemonPc.statsBase = poke.stats
                        pokemonPc.stats = calcularEstadisticas(pokemonPc)
                        //Si evoluciono con un objeto y el objeto que tiene es el mismo, se elimina el objeto.
                        for (evol in evolucionA!!.detalles!!){
                            if(evol.idTipoEvolucion == 3){
                                if(pokemonPc.idObjeto == evol.evolucionItem?.id){
                                    pokemonPc.objeto = null
                                    pokemonPc.idObjeto = null
                                }
                            }
                        }
                        val pokedexUser = user.pokedex
                        if (pokedexUser != null) {
                            val pokedexAux = pokedexUser.filter { item -> item.idPokemon == poke.id }
                            if (pokedexAux.isEmpty()) {         //Si no encuentra el pokemon en la pokedex del usuario lo agrega
                                val pokedex = UserPokedex(
                                    poke.id,
                                    poke.nombre,
                                    poke.tipo
                                )
                                user.pokedex!!.add(pokedex)
                            }
                        }
//                        preferencesManager.savePokemon(poke)
                        //pokemonData.postValue(poke!!)
                        val result = updateUserFireBase(user)
                        if (result != null) {
                            //pokemonPcData.postValue(pokemonPc!!)
                            preferencesManager.savePcPokemon(pokemonPc)
                            stateEvolucion.postValue(State.SUCCESS)
                        } else {
                            stateEvolucion.postValue(State.FAILURE)
                        }
                    }
                }
            }
        } catch (e: Exception){
            Log.d("evolucionPokemon", "Error evolucionPokemon ")
            stateEvolucion.postValue(State.FAILURE)
        }
    }

    fun addObjeto(id: Int, objeto:String){
        stateItems.postValue(State.LOADING)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val user = preferencesManager.getUserLogin()
                var pokemonPc = user.pc?.filter { item -> item.id == id }?.get(0)
                if (pokemonPc != null) {
                    if(objeto != ""){
                        val itemAux = getPokemonItemsFireBase(objeto)
                        if(itemAux != null){
                            pokemonPc = analizarObjeto(itemAux, pokemonPc)
                            //pokemonPc.objeto = itemAux.nombre
                            //pokemonPc.idObjeto = itemAux.id
                            val evolucionA = checkEvolucion(pokemonPc)
                            val result = updateUserFireBase(user)
                            if (result != null) {
                                //items.postValue(itemAux!!)
                                //pokemonPcData.postValue(pokemonPc!!)
                                preferencesManager.savePcPokemon(pokemonPc)
                                if (evolucionA != null){
                                    pokemonEvolucionA.postValue(evolucionA!!)
                                }
                                stateItems.postValue(State.SUCCESS)
                            } else {
                                stateItems.postValue(State.FAILURE)
                            }
                        } else{
                            stateItems.postValue(State.FAILURE)
                        }
                    }else{
                        pokemonPc.objeto = null
                        pokemonPc.idObjeto = null
                        val result = updateUserFireBase(user)
                        if (result != null) {
                            //items.postValue(itemAux!!)
                            //pokemonPcData.postValue(pokemonPc!!)
                            preferencesManager.savePcPokemon(pokemonPc)
                            stateItems.postValue(State.SUCCESS)
                        } else {
                            stateItems.postValue(State.FAILURE)
                        }
                    }
                }
            }
        } catch (e: Exception){
            Log.d("upLevelPokemon", "Error upLevelPokemon ")
            stateItems.postValue(State.FAILURE)
        }
    }

    fun getObjetos(){
        stateItems.postValue(State.LOADING)
        try {
            val itemRepo = PokemonItemsRepo()
            viewModelScope.launch(Dispatchers.IO) {
                val itemAux = getItemsFireBase()
                if(itemAux != null){
                    for(item in itemAux){
                        itemRepo.itemList.add(item.nombre.toString())
                    }
                    itemsList.postValue(itemRepo)
                    stateItems.postValue(State.SUCCESS)
                } else{
                    stateItems.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception){
            Log.d("getObjetos", "Error getObjetos ")
            stateItems.postValue(State.FAILURE)
        }
    }

    private suspend fun getPokemonFireBase(id: Int): Pokemon?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("Pokedex").whereEqualTo("id", id).get().await()
            if(!documents.isEmpty){
                val pokemon = documents.toObjects<Pokemon>()
                return pokemon[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

    private suspend fun updateUserFireBase(user: Usuario):Usuario?{
        val dbFb = Firebase.firestore
        val id = preferencesManager.getIdUser()
        user.id = id
        return try {
            dbFb.collection("Usuarios").document(id).set(user).await()
            preferencesManager.saveUser(user)
            user
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

    private suspend fun remuvePokemonPcFireBase(id: Int): Usuario?{
        val dbFb = Firebase.firestore
        // Obtén la MutableList `pc` del objeto Usuario
        val user = preferencesManager.getUserLogin()

        return try {
            // Elimina el elemento deseado de la lista
            user.pc?.removeIf { pc -> pc.id == id }
            // Guarda los cambios en el documento
            user.id?.let { dbFb.collection("Usuarios").document(it).set(user).await() }
            preferencesManager.saveUser(user)
            user
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

    private suspend fun getIdCadenaEvolutivaFireBase(idPokemon: Int): Int?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("Pokedex").whereEqualTo("id", idPokemon).get().await()
            if(!documents.isEmpty){
                val pokemon = documents.toObjects<Pokemon>()
                return pokemon[0].detalle?.idCadenaEvolutiva
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

    private suspend fun getPokemonEvolucionFireBase(id: Int): Evoluciones?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("PokemonEvoluciones").whereEqualTo("id", id).get().await()
            if(!documents.isEmpty){
                val cadenaEvolutiva = documents.toObjects<Evoluciones>()
                return cadenaEvolutiva[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

    private suspend fun getPokemonItemsFireBase(item:String): PokemonItems?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("PokemonItems").whereEqualTo("nombre", item).get().await()
            if(!documents.isEmpty) {
                val itemAux = documents.toObjects<PokemonItems>()
                return itemAux[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

    private suspend fun getItemsFireBase(): List<PokemonItems>?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("PokemonItems").orderBy("id", Query.Direction.ASCENDING).limit(1658).get().await()
            if(!documents.isEmpty) {
                return documents.toObjects()
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

    private suspend fun checkEvolucion(pokemon: Pc):Pokemon?{
        return try{
            var evolucionA:Pokemon?=null
            val idCadenaEvolutiva = getIdCadenaEvolutivaFireBase(pokemon.idPokemon!!)
            val evolucionList = idCadenaEvolutiva?.let { getPokemonEvolucionFireBase(it)?.cadenaEvolutiva }
            val evolucionDe = evolucionList?.filter { item -> item.idEvolucionaDe == pokemon.idPokemon!! }
            if (evolucionDe != null){
                for (item in evolucionDe){
                    for (detalle in item.detalles!!){
                        when(detalle.idTipoEvolucion){
                            1->{ // Evoluciona al subir de nivel
                                val level = detalle.nivel
                                val felicidad = detalle.felicidad
                                val afecto = detalle.afecto
                                val horaDelDia = detalle.horaDelDia
                                if (level != null){
                                    if (pokemon.nivel!! >= level ){
                                        evolucionA = getPokemonFireBase(item.id!!)
                                    }
                                } else if (felicidad != null){
                                    if (pokemon.felicidad!! >= felicidad){
                                        if (afecto != null){
                                            if ((pokemon.afecto?:0) >= afecto ){
                                                evolucionA = getPokemonFireBase(item.id!!)
                                            }
                                        }else{
                                            //chequear si es de dia o noche
                                            if (horaDelDia != ""){
                                                val calendar = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                                                if (horaDelDia == "Noche"){
                                                    if(calendar < 7 || calendar > 18 ){
                                                        evolucionA = getPokemonFireBase(item.id!!)
                                                    }
                                                }
                                                if (horaDelDia == "Día"){
                                                    if(calendar in 7..18){
                                                        evolucionA = getPokemonFireBase(item.id!!)
                                                    }
                                                }
                                            }else{
                                                evolucionA = getPokemonFireBase(item.id!!)
                                            }
                                        }
                                    }
                                }
                            }
                            3->{//evoluciona por objeto
                                val idObjeto = detalle.idEvolucionItem
                                if (idObjeto != null){
                                    if (pokemon.idObjeto == idObjeto ){
                                        evolucionA = getPokemonFireBase(item.id!!)
                                    }
                                }
                            }
                            else->{}
                        }
                    }
                }
            }
            evolucionA
        } catch (e: Exception){
            Log.d("upLevelPokemon", "Error upLevelPokemon, $e ")
            null
        }
    }

    private fun calcularEstadisticas(pokemon: Pc): Estadisticas{
        val estadisticas = Estadisticas()
        val nivel : Float = pokemon.nivel!!.toFloat()
        val statsBase = pokemon.statsBase
        val puntosEsfuerzo = pokemon.puntoEsfuerzo
        val iv = pokemon.iV
        val naturaleza = pokemon.naturaleza

        //estadística = ((2 * Estadística base + IV + (Puntos de esfuerzo / 4)) * (Nivel / 100) + 5) * Naturaleza
        //calculo de HP
        val hp = (((statsBase!!.filter { item -> item.idStats == 1 }[0].statsBase!! * 2) + iv!! + (puntosEsfuerzo!!.hp!! /4)) * (nivel /100) + 5) * (naturaleza!!.stats!!.filter { item -> item.idStats == 1 }[0].multiplicador!!)
        estadisticas.hp = hp.roundToInt()
        //calculo de Ataque
        val ataque = (((statsBase.filter { item -> item.idStats == 2 }[0].statsBase!! * 2) + iv + (puntosEsfuerzo.ataque!! /4)) * (nivel /100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 2 }[0].multiplicador!!)
        estadisticas.ataque = ataque.roundToInt()
        //calculo de Defensa
        val defensa = (((statsBase.filter { item -> item.idStats == 3 }[0].statsBase!! * 2) + iv + (puntosEsfuerzo.defensa!! /4)) * (nivel /100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 3 }[0].multiplicador!!)
        estadisticas.defensa = defensa.roundToInt()
        //calculo de Ataque Especial
        val atEsp = (((statsBase.filter { item -> item.idStats == 4 }[0].statsBase!! * 2) + iv + (puntosEsfuerzo.atEsp!! /4)) * (nivel /100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 4 }[0].multiplicador!!)
        estadisticas.atEsp = atEsp.roundToInt()
        //calculo de Defensa Especial
        val defEsp = (((statsBase.filter { item -> item.idStats == 5 }[0].statsBase!! * 2) + iv + (puntosEsfuerzo.defEsp!! /4)) * (nivel /100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 5 }[0].multiplicador!!)
        estadisticas.defEsp = defEsp.roundToInt()
        //calculo de Velocidad
        val velocidad = (((statsBase.filter { item -> item.idStats == 6 }[0].statsBase!! * 2) + iv + (puntosEsfuerzo.velocidad!! /4)) * (nivel /100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 6 }[0].multiplicador!!)
        estadisticas.velocidad = velocidad.roundToInt()

        return estadisticas
    }
    private fun analizarObjeto(items: PokemonItems, pokemonPc: Pc): Pc{
        //Falta implementar logica par remover objetos
        when(items.idCategoriaItem){
            1->{
                //Objetos de mejora de estadísticas no consumibles. Falta implementar logica. Si remuevo el objeto se deben reducir los puntos de estadistica
                pokemonPc.objeto = items.nombre
                pokemonPc.idObjeto = items.id
            }
            2->{
                //bayas reductoras de puntos de esfuerzo. suben la felizidad del pokemon
                if(items.id == 146){ //Baya Grana
                    if(pokemonPc.stats!!.hp!! >= 10){
                        pokemonPc.stats!!.hp = pokemonPc.stats!!.hp!! - 10
                    } else {
                        pokemonPc.stats!!.hp = 0
                    }
                }else if (items.id == 147){ //Baya Algama
                    if(pokemonPc.stats!!.ataque!! >= 10){
                        pokemonPc.stats!!.ataque = pokemonPc.stats!!.ataque!! - 10
                    } else {
                        pokemonPc.stats!!.ataque = 0
                    }
                }else if (items.id == 148){ //Baya Ispero
                    if(pokemonPc.stats!!.defensa!! >= 10){
                        pokemonPc.stats!!.defensa = pokemonPc.stats!!.defensa!! - 10
                    } else {
                        pokemonPc.stats!!.defensa = 0
                    }
                }else if (items.id == 149){ //Baya Meluce
                    if(pokemonPc.stats!!.atEsp!! >= 10){
                        pokemonPc.stats!!.atEsp = pokemonPc.stats!!.atEsp!! - 10
                    } else {
                        pokemonPc.stats!!.atEsp = 0
                    }
                }else if (items.id == 150){ //Baya Uvav
                    if(pokemonPc.stats!!.defEsp!! >= 10){
                        pokemonPc.stats!!.defEsp = pokemonPc.stats!!.defEsp!! - 10
                    } else {
                        pokemonPc.stats!!.defEsp = 0
                    }
                }else if (items.id == 151){ //Baya Tamate
                    if(pokemonPc.stats!!.velocidad!! >= 10){
                        pokemonPc.stats!!.velocidad = pokemonPc.stats!!.velocidad!! - 10
                    } else {
                        pokemonPc.stats!!.velocidad = 0
                    }
                }
                if (pokemonPc.felicidad!! <= 255){
                    pokemonPc.felicidad = pokemonPc.felicidad!! + 10
                }
            }

            3->{
                //bayas. id: 3 4 5 6 7 8
                pokemonPc.objeto = items.nombre
                pokemonPc.idObjeto = items.id
            }

            26->{//Vitaminas - Mejoras de estadistica
                //Puntos de esfuerzo    las vitaminas aumentan en 10 los puntos de una estadistica. 255 puntos maximos por estadisticas y un total de 510
                val statsTotal = pokemonPc.puntoEsfuerzo!!.hp!! + pokemonPc.puntoEsfuerzo!!.ataque!! + pokemonPc.puntoEsfuerzo!!.defensa!! + pokemonPc.puntoEsfuerzo!!.atEsp!! + pokemonPc.puntoEsfuerzo!!.defEsp!! + pokemonPc.puntoEsfuerzo!!.velocidad!!

                if(items.id == 45) {
                    //Mas PS aumnta la Salud en +10
                    if(statsTotal < 510){
                        if(pokemonPc.puntoEsfuerzo!!.hp!! < 245){
                            pokemonPc.puntoEsfuerzo!!.hp = pokemonPc.puntoEsfuerzo!!.hp!! + 10
                        }else{
                            pokemonPc.puntoEsfuerzo!!.hp = 255
                        }
                    }
                }else if(items.id == 46) {
                    //Proteina aumnta Ataque en +10
                    if(statsTotal < 510){
                        if(pokemonPc.puntoEsfuerzo!!.ataque!! < 245){
                            pokemonPc.puntoEsfuerzo!!.ataque = pokemonPc.puntoEsfuerzo!!.ataque!! + 10
                        }else{
                            pokemonPc.puntoEsfuerzo!!.ataque = 255
                        }
                    }
                }else if(items.id == 47) {
                    //Hierro aumnta la defensa en +10
                    if(statsTotal < 510){
                        if(pokemonPc.puntoEsfuerzo!!.defensa!! < 245){
                            pokemonPc.puntoEsfuerzo!!.defensa = pokemonPc.puntoEsfuerzo!!.defensa!! + 10
                        }else{
                            pokemonPc.puntoEsfuerzo!!.defensa = 255
                        }
                    }
                }else if(items.id == 48) {
                    //Carrburante aumnta la Velocidad en +10
                    if(statsTotal < 510){
                        if(pokemonPc.puntoEsfuerzo!!.velocidad!! < 245){
                            pokemonPc.puntoEsfuerzo!!.velocidad = pokemonPc.puntoEsfuerzo!!.velocidad!! + 10
                        }else{
                            pokemonPc.puntoEsfuerzo!!.velocidad = 255
                        }
                    }
                }else if(items.id == 49) {
                    //Calcio aumnta la At. Esp en +10
                    if(statsTotal < 510){
                        if(pokemonPc.puntoEsfuerzo!!.atEsp!! < 245){
                            pokemonPc.puntoEsfuerzo!!.atEsp = pokemonPc.puntoEsfuerzo!!.atEsp!! + 10
                        }else{
                            pokemonPc.puntoEsfuerzo!!.atEsp = 255
                        }
                    }
                }else if(items.id == 50) {
                    //Si el item es un caramelo raro aumentar el nivel
                    pokemonPc.nivel = pokemonPc.nivel!! + 1
                }else if(items.id == 51) {
                    //Mas PP aumnta un 20% de los PPs de un ataque

                }else if(items.id == 52) {
                    //Zinc aumnta la De. Esp en +10
                    if(statsTotal < 510){
                        if(pokemonPc.puntoEsfuerzo!!.defEsp!! < 245){
                            pokemonPc.puntoEsfuerzo!!.defEsp = pokemonPc.puntoEsfuerzo!!.defEsp!! + 10
                        }else{
                            pokemonPc.puntoEsfuerzo!!.defEsp = 255
                        }
                    }
                }else if(items.id == 53) {
                    //PP Maximos aumnta un 60% de los PPs de un ataque
                    pokemonPc.objeto = items.nombre
                    pokemonPc.idObjeto = items.id
                }
                //Las vitaminas suben la felicidad del pokemon
                if (pokemonPc.felicidad!! <= 99){
                    pokemonPc.felicidad = pokemonPc.felicidad!! + 5
                }else if (pokemonPc.felicidad!! <= 199){
                    pokemonPc.felicidad = pokemonPc.felicidad!! + 3
                }else if (pokemonPc.felicidad!! <= 255){
                    pokemonPc.felicidad = pokemonPc.felicidad!! + 2
                }

            }
            /*
            27->{
                //Objetos de curacion
                pokemonPc.objeto = items.nombre
                pokemonPc.idObjeto = items.id
            }
            30->{
                //Objetos de restauración de estado
                pokemonPc.objeto = items.nombre
                pokemonPc.idObjeto = items.id
            }
            */
            else ->{
                pokemonPc.objeto = items.nombre
                pokemonPc.idObjeto = items.id
            }
        }

        return pokemonPc
    }

    fun getPcPokemonByIdPokemon(id: Int){
        state.postValue(State.LOADING)
        val user = preferencesManager.getUserLogin()
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val pokeAux = user.pc?.filter { item -> item.id == id }?.get(0)
                if (pokeAux != null){
                    pokemonPcData.postValue(pokeAux!!)
                    state.postValue(State.SUCCESS)
                }else{
                    state.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception){
            Log.d("upLevelPokemon", "Error upLevelPokemon ")
            state.postValue(State.FAILURE)
        }
    }

    fun getPokemon() : Pokemon {
        return preferencesManager.getPokemon()
    }

    fun getPcPokemon() : Pc {
        return preferencesManager.getPcPokemon()
    }

}
