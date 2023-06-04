package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FragmentPcViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()

    val pokemonPc = SingleLiveEvent<PcRepo>()

    fun getPokemonPC(){
        state.postValue(State.LOADING)
        try {
            val pokedexRepo = PcRepo()
            val user = preferencesManager.getUserLogin()
            val pokemonUser = user.pc
            if (pokemonUser != null) {
                for(pokemon in pokemonUser){
                    pokedexRepo.pc.add(pokemon)
                }
//            preferencesManager.savePc(pokedexRepo)
                pokemonPc.postValue(pokedexRepo)
                state.postValue(State.SUCCESS)
            } else {
                state.postValue(State.FAILURE)
            }
        }catch (e: Exception){
            Log.d("getPokemonPC", "A ver $e")
            state.postValue(State.FAILURE)
        }
    }

}
