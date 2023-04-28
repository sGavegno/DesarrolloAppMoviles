package com.example.apppokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.entities.ActionLista
import com.example.apppokedex.entities.PokemonRepo

class FragmentPokedex : Fragment() {

    lateinit var vista : View
    lateinit var imgTitulo : ImageView
    lateinit var recPokemon : RecyclerView
    lateinit var adapter: PokemonAdapter

    var pokemonRepository : PokemonRepo = PokemonRepo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTitulo)
        return vista
    }

    override fun onStart() {
        super.onStart()

        //Acá se debe ingresar la base de datos de todos los pokemons, esta base de datos es Read Only
        adapter = PokemonAdapter(pokemonRepository.pokemon){ position ->
//          onItemClick( ) cambiar a la pantalla datos
            val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(
                pokemonRepository.pokemon[position].id)
            findNavController().navigate(action)            //accion de cambiar de pantalla
//            Snackbar.make(vista, "Clik en ${pokemonRepository.pokemon[position].nombre}",Snackbar.LENGTH_SHORT)
        }
        recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
        recPokemon.adapter = adapter

    }

}