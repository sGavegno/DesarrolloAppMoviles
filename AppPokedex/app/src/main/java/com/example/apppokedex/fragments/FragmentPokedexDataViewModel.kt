package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.Evoluciones
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.TablaTiposPokemon
import com.example.apppokedex.entities.TablaTiposRepo
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FragmentPokedexDataViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val statePokemon = SingleLiveEvent<State>()
    val stateEvolucion = SingleLiveEvent<State>()
    val stateTablaTipo = SingleLiveEvent<State>()

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)
        try {
            var result: Pokemon?
            viewModelScope.launch(Dispatchers.IO) {
                result = getPokemonFireBase(idPokemon)
                if (result != null) {
                    preferencesManager.savePokemon(result!!)
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

    fun getEvolucionesById(idEvolucion: Int){
        stateEvolucion.postValue(State.LOADING)
        try {
            var evoluciones: Evoluciones?
            viewModelScope.launch(Dispatchers.IO) {
                evoluciones = getEvolucioesFireBase(idEvolucion)
                if (evoluciones != null) {
                    preferencesManager.saveEvoluciones(evoluciones!!)
                    stateEvolucion.postValue(State.SUCCESS)
                } else {
                    stateEvolucion.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("getEvolucionesById", "A ver $e")
            stateEvolucion.postValue(State.FAILURE)
        }
    }

    private suspend fun getEvolucioesFireBase(id: Int):Evoluciones?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("PokemonEvoluciones").whereEqualTo("id", id).get().await()
            if(!documents.isEmpty){
                val evoluciones = documents.toObjects<Evoluciones>()
                return evoluciones[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Pokedex")
            null
        }
    }

    fun getTablaTipos(){
        stateTablaTipo.postValue(State.LOADING)
        try {
            var document: QuerySnapshot?
            val repoTipo = TablaTiposRepo()
            viewModelScope.launch(Dispatchers.IO) {
                document = getTablaTiposFireBase()
                if(document != null){
                    val tipos = document!!.toObjects<TablaTiposPokemon>()
                    for (tiposPokemon in tipos){
                        repoTipo.tipos.add(tiposPokemon)
                    }
                    preferencesManager.saveTablaTiposPokemon(repoTipo)
                    stateTablaTipo.postValue(State.SUCCESS)
                } else {
                    stateTablaTipo.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("getTablaTiposById", "A ver $e")
            stateTablaTipo.postValue(State.FAILURE)
        }
    }

    private suspend fun getTablaTiposFireBase(): QuerySnapshot? {
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("PokemonTipos").get().await()
            documents
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Pokedex")
            null
        }
    }

    fun getPokemon(): Pokemon{
        return preferencesManager.getPokemon()
    }

    fun getEvoluciones(): Evoluciones{
        return preferencesManager.getEvoluciones()
    }

    fun getTablaTiposPokemon(): TablaTiposRepo{
        return preferencesManager.getTablaTiposPokemon()
    }

}