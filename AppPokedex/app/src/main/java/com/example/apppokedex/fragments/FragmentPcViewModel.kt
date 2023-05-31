package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.PcRepo
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPcViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()
    val statePokemon : MutableLiveData<State> = MutableLiveData()
    val stateUsuario : MutableLiveData<State> = MutableLiveData()

    val pokemonPC: MutableLiveData<PcRepo> = MutableLiveData()

    fun getPokemonPC(){
        state.postValue(State.LOADING)
        //Lista que contiene todos los pokemons de la base de datos
        val pokedexRepo = PcRepo()

        //Traer los datos del usuario y completar el nombre del pokemon
        val user = preferencesManager.getUserLogin()
        val pokemonUser = user.pc

        if (pokemonUser != null) {
            for(pokemon in pokemonUser){
                pokedexRepo.pc.add(pokemon)
            }
        }
        pokemonPC.postValue(pokedexRepo)
    }

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)
        val dbFb = Firebase.firestore

        dbFb.collection("Pokedex")
            .whereEqualTo("id", idPokemon)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    val pokemon = documents.toObjects<Pokemon>()
//                    pokemonData.postValue(pokemon[0])
                    preferencesManager.savePokemon(pokemon[0])
                    statePokemon.postValue(State.SUCCESS)
                } else {
                    statePokemon.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                statePokemon.postValue(State.FAILURE)
            }
    }

    fun savePokemon(pokemon: Pokemon){
        preferencesManager.savePokemon(pokemon)
    }

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }

    fun getUser(): Usuario {
        return preferencesManager.getUserLogin()
    }

    fun updateUserData(user : Usuario){

        stateUsuario.postValue(State.LOADING)

        val dbFb = Firebase.firestore
        val usersCollection = dbFb.collection("Usuarios")

        val id = preferencesManager.getIdUser()

        user.id = id
        usersCollection.document(id)
            .set(user)
            .addOnSuccessListener {
                preferencesManager.saveUser(user)
                stateUsuario.postValue(State.SUCCESS)
            }
            .addOnFailureListener {
                stateUsuario.postValue(State.FAILURE)
            }
    }

}
