package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.Estadisticas
import com.example.apppokedex.entities.Habilidades
import com.example.apppokedex.entities.Naturaleza
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.UserPokedex
import com.example.apppokedex.entities.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

@HiltViewModel
class FragmentAddPokemonViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()
    val statePokemon = SingleLiveEvent<State>()

    val pokemon = SingleLiveEvent<Pokemon>()
    val pcPokemon = SingleLiveEvent<Pc>()

    fun getPokemonById(idPokemon: Int) {
        statePokemon.postValue(State.LOADING)
        try {
            var result: Pokemon?
            viewModelScope.launch(Dispatchers.IO) {
                result = getPokemonFireBase(idPokemon)
                if (result != null) {
                    for (item in result!!.habilidades!!) {
                        val habilidad = getHabilidadFireBase(item.idHabilidades!!)
                        item.detalle?.nombre = habilidad?.nombre
                    }
                    //preferencesManager.savePokemon(result!!)
                    pokemon.postValue(result!!)
                    statePokemon.postValue(State.SUCCESS)
                } else {
                    statePokemon.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("getPokemonById", "A ver $e")
            statePokemon.postValue(State.FAILURE)
        }
    }

    private suspend fun getPokemonFireBase(idPokemon: Int): Pokemon? {
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("Pokedex").whereEqualTo("id", idPokemon).get().await()
            if (!documents.isEmpty) {
                val pokemon = documents.toObjects<Pokemon>()
                return pokemon[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Pokedex")
            null
        }
    }

    private suspend fun getHabilidadFireBase(idHabilidad: Int): Habilidades? {
        val dbFb = Firebase.firestore
        return try {
            val documents =
                dbFb.collection("Habilidades").whereEqualTo("idHabilidad", idHabilidad).get()
                    .await()
            if (!documents.isEmpty) {
                val habilidad = documents.toObjects<Habilidades>()
                return habilidad[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Habilidades")
            null
        }
    }

    fun addUserPokemon(
        idPokemon: Int,
        mote: String?,
        nivel: Int,
        genero: Boolean?,
        habilidad: String,
        descripcion: String
    ) {
        state.postValue(State.LOADING)
        try {
            var usuario: Usuario?
            val pokemonPc = Pc()
            viewModelScope.launch(Dispatchers.IO) {
                val user = preferencesManager.getUserLogin()
                val pokemonAux = getPokemonFireBase(idPokemon)
                val pokemonUser = user.pc
                val size = pokemonUser?.size
                val idNewPc = if (size!! > 0) {
                    pokemonUser[size - 1].id!!.plus(1)
                } else {
                    1
                }

                val nombre: String? = mote ?: pokemonAux?.nombre
                pokemonPc.id = idNewPc
                pokemonPc.idPokemon = pokemonAux?.id
                pokemonPc.nombre = pokemonAux?.nombre
                pokemonPc.mote = nombre
                pokemonPc.tipo = pokemonAux!!.tipo!!
                pokemonPc.nivel = nivel
                pokemonPc.felicidad = pokemonAux.detalle?.felicidadBase
                pokemonPc.habilidad = habilidad
                pokemonPc.genero = genero
                pokemonPc.descripcion = descripcion

                pokemonPc.statsBase = pokemonAux.stats
                pokemonPc.puntoEsfuerzo = Estadisticas(0, 0, 0, 0, 0, 0)
                pokemonPc.iV = Random.nextInt(1, 32)
                val idNaturaleza = Random.nextInt(1, 26)
                //pokemonPc.naturaleza = Naturaleza(0, null, listOf())
                pokemonPc.naturaleza = getNaturalezaFireBase(idNaturaleza)
                //pokemonPc.stats = Estadisticas(0,0,0,0,0,0)
                pokemonPc.stats = calcularEstadisticas(pokemonPc)

                user.pc!!.add(pokemonPc)
                val pokedexUser = user.pokedex
                if (pokedexUser != null) {
                    val pokedexAux = pokedexUser.filter { item -> item.idPokemon == idPokemon }
                    if (pokedexAux.isEmpty()) {
                        val pokedex = UserPokedex(
                            pokemonAux.id,
                            pokemonAux.nombre,
                            pokemonAux.tipo
                        )
                        user.pokedex!!.add(pokedex)
                    }
                }
                usuario = updateUserFireBase(user)
                if (usuario != null) {
                    pcPokemon.postValue(pokemonPc)
                    state.postValue(State.SUCCESS)
                } else {
                    state.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("addUserPokemon", "A ver $e")
            state.postValue(State.FAILURE)
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

    private suspend fun updateUserFireBase(user: Usuario): Usuario? {
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

    private suspend fun getNaturalezaFireBase(idNaturaleza: Int): Naturaleza? {
        val dbFb = Firebase.firestore
        return try {
            val documents =
                dbFb.collection("PokemonNaturaleza").whereEqualTo("id", idNaturaleza).get().await()
            if (!documents.isEmpty) {
                val habilidad = documents.toObjects<Naturaleza>()
                return habilidad[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Habilidades")
            null
        }
    }

}