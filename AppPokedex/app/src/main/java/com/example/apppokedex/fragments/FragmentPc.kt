package com.example.apppokedex.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonPcAdapter
import com.example.apppokedex.entities.ActionListaPC
import com.example.apppokedex.entities.Pokedex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPc : Fragment(), PokemonPcAdapter.PokemonPcAdapterListener {

    private val viewModel: FragmentPcViewModel by viewModels()

    lateinit var vista : View
    lateinit var imgTitulo : ImageView
    lateinit var recPokemon : RecyclerView
    lateinit var adapter: PokemonPcAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pc, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTituloPc)
        return vista

    }

    override fun onStart() {
        super.onStart()

        Glide.with(vista).load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png").into(imgTitulo)

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val posPc = sharedPref?.getInt("pos_recycler_view_pc", 0)

        viewModel.getPokemonPC()

        viewModel.pokemonPC.observe(this){
            val adapterPokedex = it.pokedex
            adapter = PokemonPcAdapter(adapterPokedex, this)
            //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
            recPokemon.layoutManager = GridLayoutManager(context,2)
            recPokemon.scrollToPosition(posPc!!)
            recPokemon.adapter = adapter

            val swipeHandler = object : ActionListaPC(adapter, adapterPokedex) {
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recPokemon)
        }

    }

    //Funciones del adapter
    override fun onCardViewClick(pokemon: Pokedex, position: Int) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putInt("pos_recycler_view_pc", position)
                commit()
            }
        }
        val action = pokemon.id?.let {
            FragmentPcDirections.actionFragmentPcToFragmentPokemonData(it)
        }
        action?.let { findNavController().navigate(it) }            //accion de cambiar de pantalla
    }

    override fun onButtonClick(pokemon: Pokedex) {
        TODO("Not yet implemented")
    }

}