package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonRepo
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

@HiltViewModel
class FragmentAddPokemonViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()
    val statePokemon = SingleLiveEvent<State>()

    val pokemon = SingleLiveEvent<Pokemon>()
    val pcPokemon = SingleLiveEvent<Pc>()

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)
        try {
            var result: Pokemon?
            viewModelScope.launch(Dispatchers.IO) {
                result = getPokemonFireBase(idPokemon)
                if (result != null) {
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

    private suspend fun getPokemonFireBase(idPokemon: Int):Pokemon?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("Pokedex").whereEqualTo("id", idPokemon).get().await()
            if(!documents.isEmpty){
                val pokemon = documents.toObjects<Pokemon>()
                return pokemon[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Pokedex")
            null
        }
    }

    fun addUserPokemon(idPokemon: Int, mote:String?, nivel:Int, genero:Boolean?, habilidad: String){
        state.postValue(State.LOADING)
        try {
            var usuario: Usuario?
            val pokemonPc = Pc()
            viewModelScope.launch(Dispatchers.IO){
                val user = preferencesManager.getUserLogin()
                val pokemonAux = getPokemonFireBase(idPokemon)
                val pokemonUser = user.pc
                if (pokemonUser != null) {
                    val size = pokemonUser.size
                    val idNewPc = if(size > 0){
                        pokemonUser[size - 1].id!!.plus(1)
                    } else {
                        1
                    }
                    var nombre: String? = null
                    if(mote != null){
                        nombre = mote
                    } else {
                        nombre = pokemonAux?.nombre
                    }
                    pokemonPc.id = idNewPc
                    pokemonPc.idPokemon = pokemonAux?.id
                    pokemonPc.mote = nombre
                    pokemonPc.tipo = pokemonAux?.tipo
                    pokemonPc.nivel = nivel
                    pokemonPc.felicidad = pokemonAux?.detalle?.felicidadBase
                    pokemonPc.belleza = 0
                    pokemonPc.afecto = 0
                    pokemonPc.habilidad = habilidad
                    pokemonPc.objeto = null
                    pokemonPc.idObjeto = null
                    pokemonPc.genero = genero
                    pokemonPc.ataque1 = ""
                    pokemonPc.ataque2 = ""
                    pokemonPc.ataque3 = ""
                    pokemonPc.ataque4 = ""
                    user.pc!!.add(pokemonPc)
                }
                val pokedexUser = user.pokedex
                if (pokedexUser != null) {
                    val pokedexAux = pokedexUser.filter { item -> item.idPokemon == idPokemon }
                    if (pokedexAux.isEmpty()) {
                        val pokedex = UserPokedex(
                            pokemonAux?.id,
                            pokemonAux?.nombre,
                            pokemonAux?.tipo
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
        }catch (e: Exception){
            Log.d("addUserPokemon", "A ver $e")
            state.postValue(State.FAILURE)
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

}