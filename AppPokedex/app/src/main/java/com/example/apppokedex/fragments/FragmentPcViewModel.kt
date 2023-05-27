package com.example.apppokedex.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPcViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()

    val pokemonPC: MutableLiveData<PokedexRepo> = MutableLiveData()

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

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }
}