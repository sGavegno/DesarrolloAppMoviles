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
class FragmentPokedexViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()
    val stateUsuario : MutableLiveData<State> = MutableLiveData()
    val statePokemon : MutableLiveData<State> = MutableLiveData()

    val pokedex: MutableLiveData<PokedexRepo> = MutableLiveData()
    val pokemonData : MutableLiveData<Pokemon> = MutableLiveData()

    fun getPokedex(){
        state.postValue(State.LOADING)
        //Lista que contiene todos los pokemons de la base de datos
        val pokedexRepo = PokedexRepo()

        //Traer los datos del usuario y completar el nombre del pokemon
        val user = preferencesManager.getUserLogin()
        val pokedexUser = user.pokedex
        for(i in 1..1008){
            val pokemonUser = pokedexUser?.filter { item -> item.idPokemon == i }
            if (pokemonUser != null) {
                if(pokemonUser.isNotEmpty()){
                    pokedexRepo.pokedex.add(Pokedex( pokemonUser[0].idPokemon, pokemonUser[0].nombre, pokemonUser[0].tipo))
                } else {
                    pokedexRepo.pokedex.add(Pokedex( i, "??????", emptyList()))
                }
            }
        }
        pokedex.postValue(pokedexRepo)
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

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }

    fun getUser():Usuario{
        return preferencesManager.getUserLogin()
    }

}