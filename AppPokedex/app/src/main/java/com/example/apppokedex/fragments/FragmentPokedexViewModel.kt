package com.example.apppokedex.fragments

import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPokedexViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()

    val pokedex = SingleLiveEvent<PokedexRepo>()

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

    fun getUser():Usuario{
        return preferencesManager.getUserLogin()
    }

}