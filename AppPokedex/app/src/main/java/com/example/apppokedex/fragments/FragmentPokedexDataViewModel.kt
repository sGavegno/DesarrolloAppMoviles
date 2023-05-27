package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Evoluciones
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.State
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPokedexDataViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val statePokemon : MutableLiveData<State> = MutableLiveData()
    val stateEvolucion : MutableLiveData<State> = MutableLiveData()

    val pokemonData : MutableLiveData<Pokemon> = MutableLiveData()
    val pokemonEvolucionData : MutableLiveData<Evoluciones> = MutableLiveData()

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)
        stateEvolucion.postValue(State.LOADING)
        val dbFb = Firebase.firestore

        dbFb.collection("Pokedex")
            .whereEqualTo("Id", idPokemon)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    statePokemon.postValue(State.SUCCESS)
                    val pokemon = documents.toObjects<Pokemon>()
                    pokemonData.postValue(pokemon[0])
                } else {
                    statePokemon.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                statePokemon.postValue(State.FAILURE)
            }
    }

    fun getEvolucionesById(id : Int){
        stateEvolucion.postValue(State.LOADING)
        val dbFb = Firebase.firestore

        dbFb.collection("PokemonEvoluciones")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    stateEvolucion.postValue(State.SUCCESS)
                    val pokemonEvolucion = documents.toObjects<Evoluciones>()
                    pokemonEvolucionData.postValue(pokemonEvolucion[0])
                } else {
                    stateEvolucion.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                stateEvolucion.postValue(State.FAILURE)
            }
    }

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }

}