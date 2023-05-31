package com.example.apppokedex.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.UserPokedex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddPokemon : Fragment() {

    private val viewModel: FragmentAddPokemonViewModel by viewModels()

    lateinit var vista: View

    lateinit var imgTitulo: ImageView
    private lateinit var btnPcExit: Button
    private lateinit var btnPcAdd: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_add_pokemon, container, false)
        imgTitulo = vista.findViewById(R.id.imgAddPokemonTitulo)
        btnPcAdd = vista.findViewById(R.id.btnAddPokemon)
        btnPcExit = vista.findViewById(R.id.btnExit)

        return vista
    }

    override fun onStart() {
        super.onStart()

        Glide.with(vista)
            .load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png")
            .into(imgTitulo)

        val dropdownItems = arrayOf("Masculino", "Femenino")


        btnPcAdd.setOnClickListener {

            val pokemon = viewModel.getPokemon()

            val user = viewModel.getUser()
            val pokemonUser = user.pc
            if (pokemonUser != null) {
                val size = pokemonUser.size
                val idNew = pokemonUser[size - 1].id!!.plus(1)
                val pokemonPc = Pc(
                    idNew,
                    pokemon.id,
                    pokemon.nombre,              //Falta dar la opcion de agregar un Mote
                    pokemon.tipo,
                    12,                 //Falta configurar el nivel
                    null,
                    null,
                    null,
                    true,
                    "",
                    "",
                    "",
                    ""
                )
                user.pc!!.add(pokemonPc)
            }
            val pokedexUser = user.pokedex
            if (pokedexUser != null) {
                val pokedexAux = pokedexUser.filter { item -> item.idPokemon == pokemon.id }
                if (pokedexAux.isEmpty()) {
                    val pokedex = UserPokedex(
                        pokemon.id,
                        pokemon.nombre,
                        pokemon.tipo
                    )
                    user.pokedex!!.add(pokedex)
                }
            }
            viewModel.updateUserData(user)

        }

        btnPcExit.setOnClickListener {


        }

    }

}