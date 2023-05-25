package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonDetalle
import com.example.apppokedex.entities.PokemonHabilidad
import com.example.apppokedex.entities.PokemonStats
import com.example.apppokedex.entities.PokemonTipo
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
    ////////////////////////////////////////////////////////////////////////////////////////////
    fun getPokedex(){

        //Lista que contiene todos los pokemons de la base de datos
        var pokemonsList : MutableList<Pokemon> = mutableListOf()
        //Variable pokemon para levantar los datos y cargarlos en la lista
        var pokemon = Pokemon(
            altura = 0,
            expBase = 0,
            id = 0,
            nombre = "",
            peso = 0,
            tipo = emptyList(),
            stats = emptyList(),
            detalle = PokemonDetalle(0, 0, 0, 0, false, 0, 0, false,false, false),
            habilidades = emptyList()
        )

        //llamar a la base de datos
        val dbFb = Firebase.firestore

        dbFb.collection("Pokedex")
            .limit(1008)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    state.postValue(State.SUCCESS)

                    for(doc in documents){
                        pokemon.nombre = doc.getString("Nombre").toString()
                        //.....


                        pokemonsList.add(pokemon)
                    }
                    //cargar pokemonsList a Shared


                } else {
                    state.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                state.postValue(State.FAILURE)
            }

        //de acá para abajo seria un for recorriendo todos los documentos.
        /*
        for(doc in documents){
            pokemon.altura = 20
            pokemon.expBase = 263
            pokemon.id = 3
            pokemon.nombre = "venusaur"
            pokemon.peso = 1000
            pokemon.tipo = listOf(
                PokemonTipo(12, "Planta"),
                PokemonTipo(4, "Veneno")
            )
            pokemon.stats = listOf(
                PokemonStats(1, "hp", 80),
                PokemonStats(2, "Ataque", 82),
                PokemonStats(3, "Defensa", 83),
                PokemonStats(4, "Ataque Especial", 100),
                PokemonStats(5, "Defensa Especial", 100),
                PokemonStats(6, "Velocidad", 80)
            )
            pokemon.detalle.idCadenaEvolutiva = 1
            pokemon.detalle.idEvolucionaDe = 2
            pokemon.detalle.felicidadBase = 50
            pokemon.detalle.ratioCaptura = 45
            pokemon.detalle.variantes = false
            pokemon.detalle.tasaGenero = 1
            pokemon.detalle.idGeneracion = 1
            pokemon.detalle.bebe = false
            pokemon.detalle.legendario = false
            pokemon.detalle.mitico = false
            // Cargar los demás detalles
            pokemon.habilidades = listOf(
                PokemonHabilidad(65, "overgrow"),
                PokemonHabilidad(34, "chlorophyll")
            )

        }
        * */



    }
    ////////////////////////////////////////////////////////////////////////////////////////////
}