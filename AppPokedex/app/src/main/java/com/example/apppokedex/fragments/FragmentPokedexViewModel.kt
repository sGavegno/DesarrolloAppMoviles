package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonDetalle
import com.example.apppokedex.entities.State
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPokedexViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()

    val pokedex : MutableLiveData<PokedexRepo> = MutableLiveData()

    fun getPokedex(){
        state.postValue(State.LOADING)
        val dbFb = Firebase.firestore
        //Lista que contiene todos los pokemons de la base de datos
        val pokemonsList = PokedexRepo()

        dbFb.collection("Pokedex")
            .orderBy("Id")
            .limit(251) //1008
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    state.postValue(State.SUCCESS)

                    val pokemon = documents.toObjects<Pokemon>()
                    for (poke in pokemon) {
                        pokemonsList.pokemons.add(poke)
                        Log.d("Firebase", "${poke.Id} => ${poke.Nombre}")
                    }
                    //cargar pokemonsList a Shared
                    pokedex.postValue(pokemonsList)
                    //preferencesManager.savePokedex(pokemonAux)
                } else {
                    state.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                state.postValue(State.FAILURE)
            }
    }

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }

}