package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.UserPokedex
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class FragmentAddPokemon : Fragment() {

    private val viewModel: FragmentAddPokemonViewModel by viewModels()

    lateinit var vista: View

    lateinit var imgTitulo: ImageView
    private lateinit var btnPcExit: Button
    private lateinit var btnPcAdd: Button
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchMote: Switch
    private lateinit var editMote: EditText
    private lateinit var editNivel: EditText
    private lateinit var spinnerGenero: Spinner
    private lateinit var spinnerHabilidad: Spinner
    private lateinit var imgPokemon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_add_pokemon, container, false)
        imgTitulo = vista.findViewById(R.id.imgAddPokemonTitulo)
        btnPcAdd = vista.findViewById(R.id.btnAddPokemon)
        btnPcExit = vista.findViewById(R.id.btnExit)
        switchMote = vista.findViewById(R.id.swMote)

        editMote = vista.findViewById(R.id.editTextMote)
        editNivel = vista.findViewById(R.id.editTextNivel)
        spinnerGenero = vista.findViewById(R.id.spinnerGenero)
        spinnerHabilidad = vista.findViewById(R.id.spinnerHabilidad)

        imgPokemon = vista.findViewById(R.id.imgPokemonAdd)

        return vista
    }

    override fun onStart() {
        super.onStart()

        Glide.with(vista)
            .load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png")
            .into(imgTitulo)

        val pokemon = viewModel.getPokemon()

        val id = pokemon.id
        if(id != null){
            if(id < 10){
                Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${pokemon.id}.png").into(imgPokemon)
                imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${pokemon.id}.png"
            }else if(id < 100){
                Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${pokemon.id}.png").into(imgPokemon)
                imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${pokemon.id}.png"
            }else{
                Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokemon.id}.png").into(imgPokemon)
                imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokemon.id}.png"
            }
        }

        //Set editText Mote
        switchMote.isChecked = true
        switchMote.setOnCheckedChangeListener { _, isChecked ->
            // Realiza acciones según el estado del Switch
            if (isChecked) {
                // El Switch está activado
                editMote.visibility = View.VISIBLE

            } else {
                // El Switch está desactivado
                editMote.visibility = View.INVISIBLE

            }
        }

        //set Spinner Genero
        val spinnerGeneroItems = listOf("Masculino", "Femenino")
        // Crea un adaptador para el Spinner
        val adapterGenero = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerGeneroItems)
        // Opcionalmente, puedes personalizar el diseño de los elementos del Spinner
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Asigna el adaptador al Spinner
        spinnerGenero.adapter = adapterGenero

        //Get Habilidad
        val spinnerHabilidadItem = mutableListOf<String>()
        for (habilidad in pokemon.habilidades!!){
            spinnerHabilidadItem.add(habilidad.detalle!!.nombre.toString().uppercase(Locale.getDefault()))
        }
        // Crea un adaptador para el Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerHabilidadItem)
        // Opcionalmente, puedes personalizar el diseño de los elementos del Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Asigna el adaptador al Spinner
        spinnerHabilidad.adapter = adapter

        btnPcAdd.setOnClickListener {
            var idNewPc = 0
            val user = viewModel.getUser()
            val pokemonUser = user.pc
            if (pokemonUser != null) {
                val size = pokemonUser.size
                idNewPc = pokemonUser[size - 1].id!!.plus(1)
                val pokemonPc = Pc(
                    idNewPc,
                    pokemon.id,
                    pokemon.nombre,
                    pokemon.tipo,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "",
                    "",
                    "",
                    ""
                )
                val isSwitchOn = switchMote.isChecked
                if (isSwitchOn) {
                    // El Switch está activado
                    pokemonPc.mote = editMote.text.toString()
                }
                val nivel = editNivel.text.toString().toInt()
                if(nivel > 0){
                    pokemonPc.nivel = nivel
                } else {
                    pokemonPc.nivel = 1
                }
                val generoPokemon = spinnerGenero.selectedItem.toString()
                pokemonPc.genero = generoPokemon == "Masculino"
                val habilidadPokemon = spinnerHabilidad.selectedItem.toString()
                pokemonPc.habilidad = habilidadPokemon
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
            viewModel.stateUsuario.observe(this) {
                when(it){
                    State.LOADING->{
                        Snackbar.make(vista, "Procesando", Snackbar.LENGTH_SHORT).show()
                    }
                    State.SUCCESS->{
                        val action = FragmentAddPokemonDirections.actionFragmentAddPokemonToFragmentPokemonData(idNewPc)
                        findNavController().navigate(action)            //accion de cambiar de pantalla
                    }
                    State.FAILURE->{
                        Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

        btnPcExit.setOnClickListener {
            findNavController().navigateUp()            //accion de cambiar de pantalla
        }

    }

}