package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Evoluciones
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonUsuario
import com.example.apppokedex.entities.PokemonUsuarioRepo
import com.example.apppokedex.entities.State
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPokemonDataViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val statePokemon : MutableLiveData<State> = MutableLiveData()
    val stateUser : MutableLiveData<State> = MutableLiveData()

    val pokemonUserData : MutableLiveData<PokemonUsuario> = MutableLiveData()
    val pokemonData : MutableLiveData<Pokemon> = MutableLiveData()

    val dbFb = Firebase.firestore

    fun getPokemonUserById(idUser: String, idPokemon: Int){
        stateUser.postValue(State.LOADING)

        dbFb.collection("PokemonUser")
            .whereEqualTo("Id", idUser)
            .whereEqualTo("IdPokemon", idPokemon)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    stateUser.postValue(State.SUCCESS)
                    val pokeUser = documents.toObjects<PokemonUsuario>()
                    pokemonUserData.postValue(pokeUser[0])
                } else {
                    stateUser.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                stateUser.postValue(State.FAILURE)
            }
    }

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)
        stateUser.postValue(State.LOADING)

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

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }

}