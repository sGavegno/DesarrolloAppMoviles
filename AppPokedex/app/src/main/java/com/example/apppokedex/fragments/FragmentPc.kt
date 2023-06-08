package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonPcAdapter
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPc : Fragment(), PokemonPcAdapter.PokemonPcAdapterListener {

    private val viewModel: FragmentPcViewModel by viewModels()

    lateinit var vista: View
    lateinit var imgTitulo: ImageView
    lateinit var recPokemon: RecyclerView
    private lateinit var progressPc: ProgressBar
    private lateinit var btnPcAdd: Button
    lateinit var adapter: PokemonPcAdapter


    var posPc:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pc, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTituloPc)
        btnPcAdd = vista.findViewById(R.id.btnPcAdd)
        progressPc = vista.findViewById(R.id.progressBarPc)

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                State.LOADING -> {
                    progressPc.visibility = View.VISIBLE
                    recPokemon.visibility = View.INVISIBLE
                }
                State.SUCCESS -> {
                    progressPc.visibility = View.INVISIBLE
                    recPokemon.visibility = View.VISIBLE
                }
                State.FAILURE -> {
                    Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.pokemonPc.observe(viewLifecycleOwner) {
            val adapterPokedex = it.pc
            adapter = PokemonPcAdapter(adapterPokedex, this)
            //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
            recPokemon.layoutManager = GridLayoutManager(context, 2)
            recPokemon.scrollToPosition(posPc)
            recPokemon.adapter = adapter
        }

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
        posPc = sharedPref?.getInt("pos_recycler_view_pc", 0)!!

        viewModel.getPokemonPC()

        btnPcAdd.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Ingrese el ID del Pokemon")
            val input = EditText(requireContext())
            input.inputType = InputType.TYPE_CLASS_NUMBER
            alertDialog.setView(input)
            alertDialog.setPositiveButton("OK") { _, _ ->
                val idPoke = input.text.toString().toInt()
                val action = FragmentPcDirections.actionFragmentPcToFragmentAddPokemon(idPoke)
                findNavController().navigate(action)
            }
            alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            alertDialog.show()
        }

    }

    //Funciones del adapter
    override fun onCardViewClick(pokemonPc: Pc, position: Int) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putInt("pos_recycler_view_pc", position)
                commit()
            }
        }
        val action = pokemonPc.id?.let {
            FragmentPcDirections.actionFragmentPcToFragmentPokemonData(it)
        }
        action?.let { findNavController().navigate(it) }            //accion de cambiar de pantalla
    }

    override fun onButtonClick(pokemonPc: Pc) {
        TODO("Not yet implemented")
    }

}