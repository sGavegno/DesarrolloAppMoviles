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

    fun upLevelPokemon(id: Int){
        stateLvl.postValue(State.LOADING)
        val user = preferencesManager.getUserLogin()
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val pokeAux = user.pc?.filter { item -> item.id == id }?.get(0)
                if (pokeAux != null){
                    pokeAux.nivel = pokeAux.nivel!! + 1
                    pokeAux.stats = calcularEstadisticas(pokeAux)
                    //pokemonPcData.postValue(pokeAux!!)
                    preferencesManager.savePcPokemon(pokeAux)
                    val evolucionA = chekEvolucion(pokeAux)
                    if (evolucionA != null){
                        pokemonEvolucionA.postValue(evolucionA!!)
                    }
                    val result = updateUserFireBase(user)
                    if (result != null) {
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
                        preferencesManager.savePcPokemon(pokemonPc)
                        //pokemonPcData.postValue(pokemonPc!!)
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

    private fun calcularEstadisticas(pokemon: Pc): Estadisticas{
        val estadisticas = Estadisticas()
        //estadística = ((2 * Estadística base + IV + (Puntos de esfuerzo / 4)) * (Nivel / 100) + 5) * Naturaleza
        val nivel = pokemon.nivel
        val statsBase = pokemon.statsBase
        val puntosEsfuerzo = pokemon.puntoEsfuerzo
        val iv = pokemon.iV
        val naturaleza = pokemon.naturaleza

        //estadística = ((2 * Estadística base + IV + (Puntos de esfuerzo / 4)) * (Nivel / 100) + 5) * Naturaleza
        //calculo de HP
        val hp = ((statsBase!!.filter { item -> item.idStats == 1 }[0].statsBase?.times(2)!! + iv!! + (puntosEsfuerzo!!.hp!! /4)) * (nivel!!/100) + 5) * (naturaleza!!.stats!!.filter { item -> item.idStats == 1 }[0].multiplicador!!)
        estadisticas.hp = hp.roundToInt()
        //calculo de Ataque
        val ataque = ((statsBase.filter { item -> item.idStats == 2 }[0].statsBase?.times(2)!! + iv + (puntosEsfuerzo.ataque!! /4)) * (nivel/100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 2 }[0].multiplicador!!)
        estadisticas.ataque = ataque.roundToInt()
        //calculo de Defensa
        val defensa = ((statsBase.filter { item -> item.idStats == 3 }[0].statsBase?.times(2)!! + iv + (puntosEsfuerzo.defensa!! /4)) * (nivel/100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 3 }[0].multiplicador!!)
        estadisticas.defensa = defensa.roundToInt()
        //calculo de Ataque Especial
        val atEsp = ((statsBase.filter { item -> item.idStats == 4 }[0].statsBase?.times(2)!! + iv + (puntosEsfuerzo.atEsp!! /4)) * (nivel/100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 4 }[0].multiplicador!!)
        estadisticas.atEsp = atEsp.roundToInt()
        //calculo de Defensa Especial
        val defEsp = ((statsBase.filter { item -> item.idStats == 5 }[0].statsBase?.times(2)!! + iv + (puntosEsfuerzo.defEsp!! /4)) * (nivel/100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 5 }[0].multiplicador!!)
        estadisticas.defEsp = defEsp.roundToInt()
        //calculo de Velocidad
        val velocidad = ((statsBase.filter { item -> item.idStats == 6 }[0].statsBase?.times(2)!! + iv + (puntosEsfuerzo.velocidad!! /4)) * (nivel/100) + 5) * (naturaleza.stats!!.filter { item -> item.idStats == 6 }[0].multiplicador!!)
        estadisticas.velocidad = velocidad.roundToInt()

        return estadisticas
    }

    fun addObjeto(id: Int, objeto:String){
        stateItems.postValue(State.LOADING)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val user = preferencesManager.getUserLogin()
                val pokemonPc = user.pc?.filter { item -> item.id == id }?.get(0)
                if (pokemonPc != null) {
                    val itemAux = getPokemonItemsFireBase(objeto)
                    if(itemAux != null){
                        pokemonPc.objeto = itemAux.nombre
                        pokemonPc.idObjeto = itemAux.id
                        preferencesManager.savePcPokemon(pokemonPc)
                        val evolucionA = chekEvolucion(pokemonPc)
                        if (evolucionA != null){
                            pokemonEvolucionA.postValue(evolucionA!!)
                        }
                        val result = updateUserFireBase(user)
                        if (result != null) {
                            //items.postValue(itemAux!!)
                            stateItems.postValue(State.SUCCESS)
                        } else {
                            stateItems.postValue(State.FAILURE)
                        }
                    } else{
                        stateItems.postValue(State.FAILURE)
                    }
                }
            }
        } catch (e: Exception){
            Log.d("upLevelPokemon", "Error upLevelPokemon ")
            stateItems.postValue(State.FAILURE)
        }
    }

    fun addItem(id: Int, objeto:String){
        stateItems.postValue(State.LOADING)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val user = preferencesManager.getUserLogin()
                val pokemonPc = user.pc?.filter { item -> item.id == id }?.get(0)
                if (pokemonPc != null) {
                    val itemAux = getPokemonItemsFireBase(objeto)
                    if(itemAux != null){
                        pokemonPc.objeto = itemAux.nombre
                        pokemonPc.idObjeto = itemAux.id
                        preferencesManager.savePcPokemon(pokemonPc)
                        val evolucionA = chekEvolucion(pokemonPc)
                        if (evolucionA != null){
                            pokemonEvolucionA.postValue(evolucionA!!)
                        }
                        val result = updateUserFireBase(user)
                        if (result != null) {
                            //items.postValue(itemAux!!)
                            stateItems.postValue(State.SUCCESS)
                        } else {
                            stateItems.postValue(State.FAILURE)
                        }
                    } else{
                        stateItems.postValue(State.FAILURE)
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

    private suspend fun getIdCadenaEvolutiva(idPokemon: Int): Int?{
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

    private suspend fun chekEvolucion(pokemon: Pc):Pokemon?{
        return try{
            var evolucionA:Pokemon?=null
            val idCadenaEvolutiva = getIdCadenaEvolutiva(pokemon.idPokemon!!)
            val evolucionList = idCadenaEvolutiva?.let { getPokemonEvolucionFireBase(it)?.cadenaEvolutiva }
            val evolucionDe = evolucionList?.filter { item -> item.idEvolucionaDe == pokemon.idPokemon!! }
            if (evolucionDe != null){
                for (item in evolucionDe){
                    for (detalle in item.detalles!!){
                        when(detalle.idTipoEvolucion){
                            1->{
                                val level = detalle.nivel
                                val felicidad = detalle.felicidad
                                if (level != null){
                                    if (pokemon.nivel!! >= level ){
                                        evolucionA = getPokemonFireBase(item.id!!)
                                    }
                                } else if (felicidad != null){
                                    if (pokemon.felicidad!! >= felicidad){
                                        //chequear si es de dia o noche
                                        evolucionA = getPokemonFireBase(item.id!!)
                                    }
                                }
                            }
                            3->{
                                val idObjeto = detalle.idEvolucionItem
                                if (idObjeto != null){
                                    if (pokemon.idObjeto!! == idObjeto ){
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
            Log.d("upLevelPokemon", "Error upLevelPokemon ")
            null
        }
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
