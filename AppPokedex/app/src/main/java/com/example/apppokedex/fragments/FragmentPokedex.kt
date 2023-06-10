package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.adapters.SpinerTipoAdapter
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPokedex : Fragment(), PokemonAdapter.PokemonAdapterListener, SpinerTipoAdapter.SpinerTipoAdapterListener {

    private val viewModel: FragmentPokedexViewModel by viewModels()

    private var listaPokedex: MutableList<Pokedex> = mutableListOf() // Guarda la lista original de datos

    private lateinit var imgTitulo : ImageView
    private lateinit var recPokemon : RecyclerView
    private lateinit var progressPokedex : ProgressBar
    private lateinit var adapter: PokemonAdapter
    private lateinit var adapterSpiner: SpinerTipoAdapter
    private lateinit var spinnerTipo: Spinner
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchFilter: Switch

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
        spinnerTipo = vista.findViewById(R.id.spinnerTipos)
        switchFilter = vista.findViewById(R.id.switchFilter)

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
            listaPokedex = it.pokedex
            adapter = PokemonAdapter(listaPokedex, this)
            //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
            recPokemon.layoutManager = GridLayoutManager(context,2)
            recPokemon.scrollToPosition(posPokedex)
            recPokemon.adapter = adapter
        }

        viewModel.pokedexFiltrada.observe(viewLifecycleOwner){
            val pokedexFilter = it.pokedex
            adapter = PokemonAdapter(pokedexFilter, this)
            //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
            recPokemon.layoutManager = GridLayoutManager(context,2)
            recPokemon.scrollToPosition(posPokedex)
            recPokemon.adapter = adapter
        }

        viewModel.stateTablaTipo.observe(viewLifecycleOwner){
            when (it) {
                State.LOADING -> {
                    //Snackbar.make(vista, "Procesando Tipos", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS -> {
                    //Snackbar.make(vista, "Tipos Cargaos", Snackbar.LENGTH_SHORT).show()
                }
                State.FAILURE -> {
                    Snackbar.make(vista, "Fallo la carga de Tipos", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        switchFilter.setOnCheckedChangeListener { _, isChecked ->
            // Realiza acciones según el estado del Switch
            if (isChecked) {
                // El Switch está activado
                if(spinnerTipo.selectedItem != 0){
                    viewModel.filterPokedex(listaPokedex, spinnerTipo.selectedItem as Int)
                }
            } else {
                // El Switch está desactivado
                adapter = PokemonAdapter(listaPokedex, this)
                recPokemon.layoutManager = GridLayoutManager(context,2)
                recPokemon.scrollToPosition(posPokedex)
                recPokemon.adapter = adapter
            }
        }

        viewModel.getTablaTipos()

        return vista
    }

    override fun onStart() {
        super.onStart()

        switchFilter.isChecked = false
        progressPokedex.visibility = View.VISIBLE
        recPokemon.visibility = View.INVISIBLE

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        posPokedex = sharedPref?.getInt("pos_recycler_view_pokedex", 0)!!

        //Get Tipos

        val spinnerTipoItem = listOf( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18)
        // Crea un adaptador para el Spinner
        adapterSpiner = SpinerTipoAdapter(spinnerTipoItem,this)
        spinnerTipo.dropDownVerticalOffset
        spinnerTipo.adapter = adapterSpiner
/*
        val spinnerTipoItem = listOf( "No filtrar tipo", "Normal", "Lucha", "Volador", "Veneno", "Tierra", "Roca", "Bicho",
            "Fantasma", "Acero", "Fuego", "Agua", "Planta", "Electrico", "Psiquico", "Hielo", "Dragon", "Siniestro", "Hada")
        val adapterTipo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerTipoItem)
        // Opcionalmente, puedes personalizar el diseño de los elementos del Spinner
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Asigna el adaptador al Spinner
        spinnerTipo.adapter = adapterTipo
*/
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

    override fun onCardViewClick(idTipo: Int, position: Int) {
        TODO("Not yet implemented")
        //Implementar Filtro
    }

}
