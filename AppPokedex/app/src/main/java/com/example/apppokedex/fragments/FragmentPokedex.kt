package com.example.apppokedex.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.adapters.PokemonPcAdapter
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPokedex : Fragment(), PokemonAdapter.PokemonAdapterListener {

    private val viewModel: FragmentPokedexViewModel by viewModels()

    private lateinit var imgTitulo : ImageView
    private lateinit var recPokemon : RecyclerView
    private lateinit var progressPokedex : ProgressBar
    private lateinit var adapter: PokemonAdapter

    lateinit var vista : View


    var posPokedex = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTitulo)
        progressPokedex = vista.findViewById(R.id.progressBarPokedex)

        Glide.with(vista).load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png").into(imgTitulo)

        viewModel.state.observe(viewLifecycleOwner){
            when (it) {
                State.LOADING -> {
                    progressPokedex.visibility = View.VISIBLE
                    recPokemon.visibility = View.INVISIBLE
                }
                State.SUCCESS -> {
                    progressPokedex.visibility = View.INVISIBLE
                    recPokemon.visibility = View.VISIBLE
                }
                State.FAILURE -> {
                    Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.pokedex.observe(viewLifecycleOwner){
            val pokedex = it.pokedex
            adapter = PokemonAdapter(pokedex, this)
            //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
            recPokemon.layoutManager = GridLayoutManager(context,2)
            recPokemon.scrollToPosition(posPokedex)
            recPokemon.adapter = adapter
        }

        viewModel.stateTablaTipo.observe(viewLifecycleOwner){
            when (it) {
                State.LOADING -> {
                    Snackbar.make(vista, "Procesando Tipos", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS -> {
                    Snackbar.make(vista, "Tipos Cargaos", Snackbar.LENGTH_SHORT).show()
                }
                State.FAILURE -> {
                    Snackbar.make(vista, "Fallo la carga de Tipos", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.getTablaTipos()

        return vista
    }

    override fun onStart() {
        super.onStart()
        //setInputDialogListener(this)
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        posPokedex = sharedPref?.getInt("pos_recycler_view_pokedex", 0)!!

        viewModel.loadPokedex()
    }

    //Funciones del Adapter
    override fun onCardViewClick(pokemon: Pokedex, position: Int) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putInt("pos_recycler_view_pokedex", position)
                commit()
            }
        }
        if(pokemon.id != null){
            val user = viewModel.getUser()
            val pokedexUser = user.pokedex
            val pokemonUser = pokedexUser?.filter { item -> item.idPokemon == pokemon.id }
            if (pokemonUser != null) {
                if (pokemonUser.isNotEmpty()){
                    // Código para atender el clic del CardView del elemento del RecyclerView
                    val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(
                        pokemon.id
                    )
                    findNavController().navigate(action)            //accion de cambiar de pantalla
                }
            }
        }
    }

}
