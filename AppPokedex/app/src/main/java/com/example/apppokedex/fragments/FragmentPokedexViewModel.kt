package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.State
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPokedexViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()

    //val pokedex : MutableLiveData<PokedexRepo> = MutableLiveData()
    val pokedex: MutableLiveData<PokedexRepo> = MutableLiveData()

    fun getPokedex(){
        state.postValue(State.LOADING)
        val dbFb = Firebase.firestore
        //Lista que contiene todos los pokemons de la base de datos
        val pokedexRepo = PokedexRepo()

        //Traer los datos del usuario y completar el nombre del pokemon
        val user = preferencesManager.getUserLogin()
        val pokedexUser = user.pokedex
        for(i in 1..1008){
            val pokemonUser = pokedexUser?.filter { item -> item.idPokemon == i }
            if (pokemonUser != null) {
                if(pokemonUser.isNotEmpty()){
                    pokedexRepo.pokedex.add(Pokedex( i, pokemonUser[0].nombre, pokemonUser[0].tipo))
                } else {
                    pokedexRepo.pokedex.add(Pokedex( i, "??????", emptyList()))
                }
            }
        }
        pokedex.postValue(pokedexRepo)
    }

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }

}