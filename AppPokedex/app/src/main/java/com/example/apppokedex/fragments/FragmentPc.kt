package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonPcAdapter
import com.example.apppokedex.database.InputDialogListener
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonHabilidad
import com.example.apppokedex.entities.Pokemons
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.UserPokedex
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPc : Fragment(), PokemonPcAdapter.PokemonPcAdapterListener {

    private val viewModel: FragmentPcViewModel by viewModels()

    lateinit var vista: View
    lateinit var imgTitulo: ImageView
    lateinit var recPokemon: RecyclerView
    private lateinit var btnPcAdd: Button
    lateinit var adapter: PokemonPcAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pc, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTituloPc)
        btnPcAdd = vista.findViewById(R.id.btnPcAdd)
        return vista

    }

    override fun onStart() {
        super.onStart()

        Glide.with(vista)
            .load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png")
            .into(imgTitulo)

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        val posPc = sharedPref?.getInt("pos_recycler_view_pc", 0)

        viewModel.getPokemonPC()

        viewModel.pokemonPC.observe(this) {
            val adapterPokedex = it.pokedex
            adapter = PokemonPcAdapter(adapterPokedex, this)
            //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
            recPokemon.layoutManager = GridLayoutManager(context, 2)
            recPokemon.scrollToPosition(posPc!!)
            recPokemon.adapter = adapter
            /*
            val swipeHandler = object : ActionListaPC(adapter, adapterPokedex) {
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recPokemon)
            */
        }

        btnPcAdd.setOnClickListener {

            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Ingrese el ID del Pokemon")
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

            viewModel.pokemonData.observe(this) {
                viewModel.savePokemon(it)

                val action = FragmentPcDirections.actionFragmentPcToFragmentAddPokemon()
                findNavController().navigate(action)            //accion de cambiar de pantalla

            }
        }

    }

    //Funciones del adapter
    override fun onCardViewClick(pokemon: Pokedex, position: Int) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        if (sharedPref != null) {
            with(sharedPref.edit()) {
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

    //Funcion para dialogos

    //Funciones del boton add pokemon

    private fun showInputDialogId() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese el ID del Pokemon Capturado")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        alertDialog.setView(input)
        alertDialog.setPositiveButton("OK") { _, _ ->
            val idPoke = input.text.toString().toInt()

        }
        alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }

    private fun showInputDialogMote(poke: Pc,pokemon: Pokemon) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese un Mote del Pokemon")
        val input = EditText(requireContext())
        input.setText(pokemon.nombre)
        alertDialog.setView(input)
        alertDialog.setPositiveButton("Confirmar") { _, _ ->
            poke.mote = input.text.toString()

        }
        alertDialog.setNegativeButton("Cancelar") { _, _ ->
//            dialog.cancel()
        }
        alertDialog.show()
    }

    private fun showInputDialogNivel(poke: Pc, pokemon: Pokemon) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese el NIvel del Pokemon")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.keyListener =
            DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9
        alertDialog.setView(input)
        alertDialog.setPositiveButton("OK") { _, _ ->
            poke.nivel = input.text.toString().toInt()
        }
        alertDialog.show()
    }

    private fun showInputDialogGenero(poke: Pc, pokemon: Pokemon) {
        val options = arrayOf("Masculino", "Femenino")

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Seleccione el genero del Pokemon")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        alertDialog.setView(input)
        alertDialog.setItems(options) { dialog, which ->
            poke.genero = which == 0
        }
        alertDialog.show()
    }

    private fun showInputDialogHabilidad(poke: Pc, pokemon: Pokemon) {
        val habilidadAux: MutableList<String> = mutableListOf()

        for (habilidad in pokemon.habilidades!!){
            habilidadAux.add(habilidad.detalle?.nombre.toString())
        }

        val options = habilidadAux.toTypedArray()

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Seleccione la habilidad del Pokemon")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        alertDialog.setView(input)
        alertDialog.setItems(options) { dialog, which ->
            poke.habilidad = options[which]
        }
        alertDialog.show()
    }

}