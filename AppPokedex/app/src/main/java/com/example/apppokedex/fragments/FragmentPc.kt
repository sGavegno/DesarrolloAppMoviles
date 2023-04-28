package com.example.apppokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.entities.ActionLista
import com.example.apppokedex.entities.PokemonRepo

class FragmentPc : Fragment() {

    lateinit var vista : View
    lateinit var imgTitulo : ImageView
    lateinit var btnAddPokemon : Button
    lateinit var recPokemon : RecyclerView
    lateinit var adapter: PokemonAdapter

    var pokemonRepository : PokemonRepo = PokemonRepo()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_pc, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTituloPc)
        btnAddPokemon = vista.findViewById(R.id.btnAddPokemonPc)
        return vista

    }

    override fun onStart() {
        super.onStart()

        btnAddPokemon.setOnClickListener{
            //crear nueva entrada a la base de datos y pasar la posicion como parametro
            val action = FragmentPcDirections.actionFragmentPcToFragmentPokemonData(0)
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }

        //Se debe levantar la base de datos del usuario con los pokemons cargados.
        // Esta base de datos solo la puede modificar el propio usuario y solo algunos parametros como el mote, peso y altura
        adapter = PokemonAdapter(pokemonRepository.pokemon){ position ->

            val action = FragmentPcDirections.actionFragmentPcToFragmentPokemonData(
                pokemonRepository.pokemon[position].id
            )
            findNavController().navigate(action)            //accion de cambiar de pantalla
//            Snackbar.make(vista, "Clik en ${pokemonRepository.pokemon[position].nombre}",Snackbar.LENGTH_SHORT)
        }
        recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
        recPokemon.adapter = adapter

        val swipeHandler = object : ActionLista(adapter) {
            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                // Permitir que se deslice solo en ciertos elementos del RecyclerView
                return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recPokemon)

    }

}