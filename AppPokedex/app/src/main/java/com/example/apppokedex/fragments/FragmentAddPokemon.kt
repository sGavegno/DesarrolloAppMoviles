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

        viewModel.state.observe(viewLifecycleOwner) {
            when(it){
                State.LOADING->{
                    Snackbar.make(vista, "Procesando", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS->{

                }
                State.FAILURE->{
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
                    Snackbar.make(vista, "Procesando", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS->{
                    Snackbar.make(vista, "Carga Exitosa", Snackbar.LENGTH_SHORT).show()
                }
                State.FAILURE->{
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

        Glide.with(vista)
            .load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png")
            .into(imgTitulo)

        val idPcPokemon = FragmentAddPokemonArgs.fromBundle(requireArguments()).idPokemon
        viewModel.getPokemonById(idPcPokemon)

        //Set editText Mote
        switchMote.isChecked = true


        btnPcAdd.setOnClickListener {

            var mote: String? = null
            val isSwitchOn = switchMote.isChecked
            if (isSwitchOn) {
                // El Switch está activado
                mote = editMote.text.toString()
            }
            val nivelAux = editNivel.text.toString().toInt()
            var nivel: Int = 1
            if(nivelAux > 0){
                nivel = nivelAux
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
            val habilidadPokemon = spinnerHabilidad.selectedItem.toString()
            viewModel.addUserPokemon(idPcPokemon, mote, nivel, genero, habilidadPokemon)
        }

        btnPcExit.setOnClickListener {
            findNavController().navigateUp()            //accion de cambiar de pantalla
        }

    }

}