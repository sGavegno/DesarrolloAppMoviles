package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.Evoluciones
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonTipo
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.TablaTiposPokemon
import com.example.apppokedex.entities.TablaTiposRepo
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FragmentPokedexDataViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val statePokemon = SingleLiveEvent<State>()

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)
        try {
            var result: Pokemon?
            viewModelScope.launch(Dispatchers.IO) {
                result = getPokemonFireBase(idPokemon)
                if (result != null) {
                    preferencesManager.savePokemon(result!!)
                    statePokemon.postValue(State.SUCCESS)
                } else {
                    statePokemon.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("getPokemonById", "A ver $e")
            statePokemon.postValue(State.FAILURE)
        }
    }

    private suspend fun getPokemonFireBase(idPokemon: Int):Pokemon?{
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("Pokedex").whereEqualTo("id", idPokemon).get().await()
            if(!documents.isEmpty){
                val pokemon = documents.toObjects<Pokemon>()
                return pokemon[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Pokedex")
            null
        }
    }

    fun getPokemon(): Pokemon{
        return preferencesManager.getPokemon()
    }

    fun getTablaTiposPokemon(): TablaTiposRepo{
        return preferencesManager.getTablaTiposPokemon()
    }


    fun analizarDebilidad(tipo: List<PokemonTipo>): MutableList<Int>{
        val tablaTipo = getTablaTiposPokemon().tipos
        val debilidadList: MutableList<Int> = mutableListOf()

        for(pokemonTipo in tipo){
            val tipoAux = tablaTipo.filter { item -> item.idTipo == pokemonTipo.idTipo }
            for(itemTipo in tipoAux) {
                for (aux in itemTipo.danio!!.debil!!){
                    if (debilidadList.none { item -> item == aux.idTipo }){
                        aux.idTipo?.let { debilidadList.add(it) }
                    }
                }
            }
        }
        return debilidadList
    }

    fun analizarEfectividad(tipo: List<PokemonTipo>): MutableList<Int>{
        val tablaTipo = getTablaTiposPokemon().tipos
        val efectividadList: MutableList<Int> = mutableListOf()

        for(pokemonTipo in tipo){
            val tipoAux = tablaTipo.filter { item -> item.idTipo == pokemonTipo.idTipo }
            for(itemTipo in tipoAux) {
                for (aux in itemTipo.danio!!.efectivo!!){
                    if (efectividadList.none { item -> item == aux.idTipo }){
                        aux.idTipo?.let { efectividadList.add(it) }
                    }
                }
            }
        }
        return efectividadList
    }

    fun analizarNoEfectivo(tipo: List<PokemonTipo>): MutableList<Int>{
        val tablaTipo = getTablaTiposPokemon().tipos
        val noEfectivoList: MutableList<Int> = mutableListOf()

        for(pokemonTipo in tipo){
            val tipoAux = tablaTipo.filter { item -> item.idTipo == pokemonTipo.idTipo }
            for(itemTipo in tipoAux) {
                for (aux in itemTipo.danio!!.noEfectivo!!){
                    if (noEfectivoList.none { item -> item == aux.idTipo }){
                        aux.idTipo?.let { noEfectivoList.add(it) }
                    }
                }
            }
        }
        return noEfectivoList
    }

    fun analizarInmune(tipo: List<PokemonTipo>): MutableList<Int>{
        val tablaTipo = getTablaTiposPokemon().tipos
        val inmuneList: MutableList<Int> = mutableListOf()

        for(pokemonTipo in tipo){
            val tipoAux = tablaTipo.filter { item -> item.idTipo == pokemonTipo.idTipo }
            for(itemTipo in tipoAux) {
                for (aux in itemTipo.danio!!.inmune!!){
                    if (inmuneList.none { item -> item == aux.idTipo }){
                        aux.idTipo?.let { inmuneList.add(it) }
                    }
                }
            }
        }
        return inmuneList
    }


}