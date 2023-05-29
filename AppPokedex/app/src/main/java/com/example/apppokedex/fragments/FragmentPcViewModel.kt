package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
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

    val pokemonPC: MutableLiveData<PokedexRepo> = MutableLiveData()
    val pokemonData : MutableLiveData<Pokemon> = MutableLiveData()
    val stateUsuario : MutableLiveData<State> = MutableLiveData()

    fun getPokemonPC(){
        state.postValue(State.LOADING)
        //Lista que contiene todos los pokemons de la base de datos
        val pokedexRepo = PokedexRepo()

        //Traer los datos del usuario y completar el nombre del pokemon
        val user = preferencesManager.getUserLogin()
        val pokemonUser = user.pokedex

        if (pokemonUser != null) {
            for(pokemon in pokemonUser){
                if(pokemon.inPc == true){
                    pokedexRepo.pokedex.add(Pokedex( pokemon.idPokemon, pokemon.nombre, pokemon.tipo))
                }
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
            .addOnFailureListener { exception ->
                stateUsuario.postValue(State.FAILURE)
            }
    }

}