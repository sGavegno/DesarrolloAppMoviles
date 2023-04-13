package com.example.apppokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.entities.PokemonRepo

class FragmentPokedex : Fragment() {

    lateinit var vista : View
    lateinit var recPokemon : RecyclerView
    lateinit var adapter: PokemonAdapter

    var pokemonRepository : PokemonRepo = PokemonRepo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxe)
        return vista
    }

    override fun onStart() {
        super.onStart()

        adapter = PokemonAdapter(pokemonRepository.pokemon){ position ->
//          onItemClick( ) cambiar a la pantalla datos
            val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokemonData(
                pokemonRepository.pokemon[position])
            findNavController().navigate(action)            //accion de cambiar de pantalla
//            Snackbar.make(vista, "Clik en ${pokemonRepository.pokemon[position].nombre}",Snackbar.LENGTH_SHORT)
        }
        recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
        recPokemon.adapter = adapter

    }

}