package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.PokedexRepo
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.TablaTiposPokemon
import com.example.apppokedex.entities.TablaTiposRepo
import com.example.apppokedex.entities.Usuario
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
class FragmentPokedexViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()
    val stateTablaTipo = SingleLiveEvent<State>()

    val pokedex = SingleLiveEvent<PokedexRepo>()
    val pokedexFiltrada = SingleLiveEvent<PokedexRepo>()

    fun loadPokedex(){
        state.postValue(State.LOADING)
        //Lista que contiene todos los pokemons de la base de datos
        val pokedexRepo = PokedexRepo()
        //Traer los datos del usuario y completar el nombre del pokemon
        val user = preferencesManager.getUserLogin()
        val pokedexUser = user.pokedex
        if (pokedexUser != null){
            for(i in 1..1008){
                val pokemonUser = pokedexUser.filter { item -> item.idPokemon == i }
                if(pokemonUser.isNotEmpty()){
                    pokedexRepo.pokedex.add(Pokedex( pokemonUser[0].idPokemon, pokemonUser[0].nombre, pokemonUser[0].tipo, pokemonUser[0].generacion))
                } else {
                    pokedexRepo.pokedex.add(Pokedex( i, "??????", emptyList(), null))
                }
            }
            pokedex.postValue(pokedexRepo)
            state.postValue(State.SUCCESS)
        } else {
            state.postValue(State.FAILURE)
        }
    }

    fun getTablaTipos(){
        stateTablaTipo.postValue(State.LOADING)
        try {
            var document: QuerySnapshot?
            val repoTipo = TablaTiposRepo()
            viewModelScope.launch(Dispatchers.IO) {
                document = getTablaTiposFireBase()
                if(document != null){
                    val tipos = document!!.toObjects<TablaTiposPokemon>()
                    for (tiposPokemon in tipos){
                        repoTipo.tipos.add(tiposPokemon)
                    }
                    preferencesManager.saveTablaTiposPokemon(repoTipo)
                    stateTablaTipo.postValue(State.SUCCESS)
                } else {
                    stateTablaTipo.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            Log.d("getTablaTiposById", "A ver $e")
            stateTablaTipo.postValue(State.FAILURE)
        }
    }

    private suspend fun getTablaTiposFireBase(): QuerySnapshot? {
        val dbFb = Firebase.firestore
        return try {
            val documents = dbFb.collection("PokemonTipos").get().await()
            documents
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: Pokedex")
            null
        }
    }

    fun getUser():Usuario{
        return preferencesManager.getUserLogin()
    }


    fun filterPokedex(listPokedex: MutableList<Pokedex>, tipoFilter: String){
        stateTablaTipo.postValue(State.LOADING)
        try {
            val repoPokedex = PokedexRepo()
            viewModelScope.launch(Dispatchers.IO) {
                if(tipoFilter != "No filtrar tipo"){
                    for(pokemon in listPokedex){        //No utililzo filter porque tengo objetos de tipo null y al comparar rompe
                        if(pokemon.tipo != null){
                            for(tipo in pokemon.tipo!!){
                                if (tipo.detalle?.nombre == tipoFilter){
                                    repoPokedex.pokedex.add(pokemon)
                                }
                            }
                        }
                    }
                }
                pokedexFiltrada.postValue(repoPokedex)
            }
        } catch (e: Exception) {
            Log.d("getTablaTiposById", "A ver $e")
        }
    }

}