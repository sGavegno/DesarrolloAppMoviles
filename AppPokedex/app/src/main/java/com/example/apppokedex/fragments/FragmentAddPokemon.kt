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
import android.widget.ProgressBar
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
    private lateinit var progressBarLouding : ProgressBar

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
        progressBarLouding = vista.findViewById(R.id.progressBarAddPokemon)

        Glide.with(vista)
            .load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png")
            .into(imgTitulo)

        viewModel.state.observe(viewLifecycleOwner) {
            when(it){
                State.LOADING->{
                    switchMote.visibility = View.INVISIBLE
                    progressBarLouding.visibility = View.VISIBLE
                    editMote.visibility = View.INVISIBLE
                    editNivel.visibility = View.INVISIBLE
                    spinnerGenero.visibility = View.INVISIBLE
                    spinnerHabilidad.visibility = View.INVISIBLE
                    Snackbar.make(vista, "Procesando", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS->{
                    progressBarLouding.visibility = View.INVISIBLE

                }
                State.FAILURE->{
                    progressBarLouding.visibility = View.INVISIBLE
                    Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.pcPokemon.observe(viewLifecycleOwner){
            val action = FragmentAddPokemonDirections.actionFragmentAddPokemonToFragmentPokemonData(
                it.id!!
            )
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }

        viewModel.statePokemon.observe(viewLifecycleOwner) {
            when(it){
                State.LOADING->{
                    switchMote.visibility = View.INVISIBLE
                    progressBarLouding.visibility = View.VISIBLE
                    editMote.visibility = View.INVISIBLE
                    editNivel.visibility = View.INVISIBLE
                    spinnerGenero.visibility = View.INVISIBLE
                    spinnerHabilidad.visibility = View.INVISIBLE
                }
                State.SUCCESS->{
                    switchMote.visibility = View.VISIBLE
                    progressBarLouding.visibility = View.INVISIBLE
                    editMote.visibility = View.VISIBLE
                    editNivel.visibility = View.VISIBLE
                    spinnerGenero.visibility = View.VISIBLE
                    spinnerHabilidad.visibility = View.VISIBLE
                }
                State.FAILURE->{
                    progressBarLouding.visibility = View.INVISIBLE
                    Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.pokemon.observe(viewLifecycleOwner){
            val id = it.id
            if(id != null){
                if(id < 10){
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${id}.png").into(imgPokemon)
                    imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${id}.png"
                }else if(id < 100){
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${id}.png").into(imgPokemon)
                    imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${id}.png"
                }else{
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${id}.png").into(imgPokemon)
                    imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${id}.png"
                }
            }

            //Get Habilidad
            val spinnerHabilidadItem = mutableListOf<String>()
            for (habilidad in it.habilidades!!){
                spinnerHabilidadItem.add(habilidad.detalle!!.nombre.toString().uppercase(Locale.getDefault()))
            }
            // Crea un adaptador para el Spinner
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerHabilidadItem)
            // Opcionalmente, puedes personalizar el diseño de los elementos del Spinner
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Asigna el adaptador al Spinner
            spinnerHabilidad.adapter = adapter

            val spinnerGeneroItems: MutableList<String> = mutableListOf()
            if (it.detalle?.legendario == false){
                //set Spinner Genero
                spinnerGeneroItems.add("Masculino")
                spinnerGeneroItems.add("Femenino")
            } else {
                spinnerGeneroItems.add("Legendario")
            }
            // Crea un adaptador para el Spinner
            val adapterGenero = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerGeneroItems)
            // Opcionalmente, puedes personalizar el diseño de los elementos del Spinner
            adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Asigna el adaptador al Spinner
            spinnerGenero.adapter = adapterGenero
        }

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

        return vista
    }

    override fun onStart() {
        super.onStart()

        switchMote.isChecked = true
        progressBarLouding.visibility = View.VISIBLE

        val idPcPokemon = FragmentAddPokemonArgs.fromBundle(requireArguments()).idPokemon
        viewModel.getPokemonById(idPcPokemon)

        btnPcAdd.setOnClickListener {

            var mote: String? = null
            val isSwitchOn = switchMote.isChecked
            if (isSwitchOn) {
                mote = editMote.text.toString()
            }
            var nivel = 1
            if(editNivel.text.isNotEmpty()){
                nivel = editNivel.text.toString().toInt()
            }

            //Si es legendario no tiene genero
            val genero:Boolean? = when(spinnerGenero.selectedItem.toString()){
                "Masculino"->{
                    true
                }
                "Femenino"->{
                    false
                }
                else->{
                    null
                }
            }
            val textDescripcion = "Se camputo el dia ../../.. a las ..:.. en _UBICACION_, al nivel $nivel."

            val habilidadPokemon = spinnerHabilidad.selectedItem.toString()
            viewModel.addUserPokemon(
                idPcPokemon,
                mote,
                nivel,
                genero,
                habilidadPokemon,
                textDescripcion
            )
        }

        btnPcExit.setOnClickListener {
            findNavController().navigateUp()            //accion de cambiar de pantalla
        }

    }

}