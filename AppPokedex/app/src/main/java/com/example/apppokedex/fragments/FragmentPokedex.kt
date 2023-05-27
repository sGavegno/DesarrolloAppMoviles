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
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.entities.PokemonUser
import com.example.apppokedex.entities.Pokemons
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPokedex : Fragment(), PokemonAdapter.PokemonAdapterListener,InputDialogListener {

    private val viewModel: FragmentPokedexViewModel by viewModels()

    private lateinit var imgTitulo : ImageView
    private lateinit var btnPokemdexAdd : Button
    private lateinit var recPokemon : RecyclerView
    private lateinit var adapter: PokemonAdapter

    lateinit var vista : View

    private var inputDialogListener: InputDialogListener? = null

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
        setInputDialogListener(this)
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val posPokedex = sharedPref?.getInt("pos_recycler_view_pokedex", 0)

        //Implementar a futuro, No todos los usuarios van a ver los mismos pokemon en la Pokedex
        val idUser = viewModel.getIdUser()
        //////////////////////////////////////////////////////////////////////////////////////

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
        btnPokemdexAdd.visibility = View.INVISIBLE

        btnPokemdexAdd.setOnClickListener{
            onClick()
        }

    }

    private fun setInputDialogListener(listener: InputDialogListener) {
        this.inputDialogListener = listener
    }

    //Funciones del boton add pokemon
    private fun onClick() {
        showInputDialog()
    }

    private fun showInputDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese el ID del Pokemon")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        alertDialog.setView(input)
        alertDialog.setPositiveButton("OK") { _, _ ->
            val idPoke = input.text.toString().toInt()
            inputDialogListener?.onInputDone(idPoke)
        }
        alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }

    override fun onInputDone(input: Int) {
        val imgUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$input.png"
        val newPokemon = Pokemons(0,input,"Nombre","tipo","debilidad",imgUrl,"Descripcion","Altura","Peso","categoria","Habilidad",0,0,0)
/*
        val pokemon = pokemonDao?.fetchPokemonByIdPokemon(input)
        if(pokemon == null)
        {
            //pokemonDao?.insertPokemon(newPokemon)
            val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(input)
            findNavController().navigate(action)            //accion de cambiar de pantalla
        } else {
            Snackbar.make(vista, "El Id: $input ya se encuentra en la base de datos", Snackbar.LENGTH_SHORT).show()
        }
*/
    }

    override fun onInputStringDone(input: String) {
        TODO("Not yet implemented")
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

    //Agrega el pokemon a la lista del usuario No se usa más
    override fun onButtonClick(pokemon: Pokedex) {

        val idUser = viewModel.getIdUser()
/*
        val checkPokemonUser = checkUserPokemon(id)
        if(checkPokemonUser == null)
        {
            val idPokemon = pokemon.id
            if(idPokemon != null){

                Snackbar.make(vista, "${pokemon.name} ha sido agregado correctamente", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            Snackbar.make(vista, "${pokemon.name} ya esa en la lista.", Snackbar.LENGTH_SHORT).show()
        }
*/
    }

}
