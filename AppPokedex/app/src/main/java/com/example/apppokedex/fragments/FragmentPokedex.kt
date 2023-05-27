package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.database.*
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPokedex : Fragment(), PokemonAdapter.PokemonAdapterListener {

    private val viewModel: FragmentPokedexViewModel by viewModels()

    private lateinit var imgTitulo : ImageView
    private lateinit var btnPokemdexAdd : Button
    private lateinit var recPokemon : RecyclerView
    private lateinit var adapter: PokemonAdapter

    lateinit var vista : View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTitulo)
        btnPokemdexAdd = vista.findViewById(R.id.btnPokedexAdd)

        return vista
    }

    override fun onStart() {
        super.onStart()
        //setInputDialogListener(this)
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val posPokedex = sharedPref?.getInt("pos_recycler_view_pokedex", 0)

        Glide.with(vista).load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png").into(imgTitulo)

        viewModel.getPokedex()

        viewModel.pokedex.observe(this){
            val adapterPokedex = it.pokedex
            adapter = PokemonAdapter(adapterPokedex, this)
            //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
            recPokemon.layoutManager = GridLayoutManager(context,2)
            recPokemon.scrollToPosition(posPokedex!!)
            recPokemon.adapter = adapter
        }
        btnPokemdexAdd.visibility = View.VISIBLE

        btnPokemdexAdd.setOnClickListener{
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Ingrese el ID del Pokemon Capturado")
            val input = EditText(requireContext())
            input.inputType = InputType.TYPE_CLASS_NUMBER
            alertDialog.setView(input)
            alertDialog.setPositiveButton("OK") { _, _ ->
                val idPoke = input.text.toString().toInt()
                viewModel.getPokemonById(idPoke)
            }
            alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            alertDialog.show()
        }

        viewModel.pokemonData.observe(this){
            val user = viewModel.getUser()
            val pokedexUser = user.pokedex
            if (pokedexUser != null) {
                val pokemonUser = pokedexUser.filter { item -> item.idPokemon == it.id }
                if (pokemonUser.isEmpty()) {
                    val pokemon = Pc(
                        it.id,
                        it.nombre,
                        it.tipo,
                        true,
                        it.nombre,         //Falta dar la opcion de agregar un Mote
                        12,             //Falta configurar el nivel
                        null,
                        null,
                        true,
                        "",
                        "",
                        "",
                        ""
                    )
                    user.pokedex!!.add(pokemon)
                    viewModel.updateUserData(user)
                } else {
                    Snackbar.make(vista, "El Id: ${it.id} ya se encuentra en la base de datos", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.stateUsuario.observe(this){
            when(it){
                State.LOADING->{
                    Snackbar.make(vista, "Procesando captura", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS->{
                    Snackbar.make(vista, "Carga Exitosa", Snackbar.LENGTH_SHORT).show()
                    //Refrescar pantalla
                    //val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(idPokemon)
                    //findNavController().navigate(action)            //accion de cambiar de pantalla
                }
                State.FAILURE->{
                    Snackbar.make(vista, "Error en la carga del pokemon", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

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
            // Código para atender el clic del CardView del elemento del RecyclerView
            val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(
                pokemon.id
            )
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }
    }


}
