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
class FragmentPc : Fragment(), PokemonPcAdapter.PokemonPcAdapterListener,InputDialogListener {

    private val viewModel: FragmentPcViewModel by viewModels()

    lateinit var vista: View
    lateinit var imgTitulo: ImageView
    lateinit var recPokemon: RecyclerView
    private lateinit var btnPcAdd: Button
    lateinit var adapter: PokemonPcAdapter

    private var inputDialogListener: InputDialogListener? = null

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
        setInputDialogListener(this)
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

            onClick()

            var idPokemon = 0
            viewModel.pokemonData.observe(this) {

                idPokemon = it.id!!
                val user = viewModel.getUser()
                val pokemonUser = user.pc
                if (pokemonUser != null) {

                    val size = pokemonUser.size
                    val idNew = pokemonUser[size - 1].id!!.plus(1)
                    val pokemonPc = Pc(
                        idNew,
                        it.id,
                        it.nombre,              //Falta dar la opcion de agregar un Mote
                        it.tipo,
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
                    onClick2(pokemonPc, it)
                }
            }

            viewModel.stateUsuario.observe(this) {
                when (it) {
                    State.LOADING -> {
                        Snackbar.make(vista, "Procesando captura", Snackbar.LENGTH_SHORT).show()
                    }

                    State.SUCCESS -> {
                        Snackbar.make(vista, "Carga Exitosa", Snackbar.LENGTH_SHORT).show()
                        //Refrescar pantalla
                        val action =
                            FragmentPcDirections.actionFragmentPcToFragmentPokemonData(idPokemon)
                        findNavController().navigate(action)            //accion de cambiar de pantalla
                    }

                    State.FAILURE -> {
                        Snackbar.make(vista, "Error en la carga del pokemon", Snackbar.LENGTH_SHORT)
                            .show()
                    }

                    else -> {}
                }
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
    private fun setInputDialogListener(listener: InputDialogListener) {
        this.inputDialogListener = listener
    }

    //Funciones del boton add pokemon
    private fun onClick() {
        showInputDialogId()
        //showInputDialogMote(poke, pokemon)
        //showInputDialogNivel(poke)
        //showInputDialogGenero(poke)
        //showInputDialogHabilidad(poke)
    }

    private fun onClick2(poke: Pc,pokemon: Pokemon) {
//        showInputDialogId()
        showInputDialogMote(poke, pokemon)
        //showInputDialogNivel(poke)
        //showInputDialogGenero(poke)
        //showInputDialogHabilidad(poke)
    }

    private fun showInputDialog(Id: Int) {
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

    private fun showInputDialogId() {
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

    private fun showInputDialogMote(poke: Pc,pokemon: Pokemon) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese un Mote del Pokemon")
        val input = EditText(requireContext())
        input.setText(pokemon.nombre)
        alertDialog.setView(input)
        alertDialog.setPositiveButton("Confirmar") { _, _ ->
            poke.mote = input.text.toString()
            inputDialogListener?.onInputMoteDone(poke, pokemon)
        }
        alertDialog.setNegativeButton("Cancelar") { _, _ ->
            inputDialogListener?.onInputMoteDone(poke, pokemon)
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
            inputDialogListener?.onInputNivelDone(poke, pokemon)
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
            poke.genero = which == 1
            inputDialogListener?.onInputGeneroDone(poke, pokemon)
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
            inputDialogListener?.onInputHabilidadDone(poke, pokemon)
        }
        alertDialog.show()
    }

    override fun onInputDone(input: Int) {

        /*
                val imgUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$input.png"
                val newPokemon = Pokemons(0,input,"Nombre","tipo","debilidad",imgUrl,"Descripcion","Altura","Peso","categoria","Habilidad",0,0,0)
                val pokemon = pokemonDao?.fetchPokemonByIdPokemon(input)
                if(pokemon == null)
                {
                    pokemonDao?.insertPokemon(newPokemon)
                    val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(input)
                    findNavController().navigate(action)            //accion de cambiar de pantalla
                } else {
                    Snackbar.make(vista, "El Id: $input ya se encuentra en la base de datos", Snackbar.LENGTH_SHORT).show()
                }
        */
    }

    override fun onInputMoteDone(poke: Pc, pokemon: Pokemon) {
        showInputDialogNivel(poke, pokemon)
    }

    override fun onInputNivelDone(poke: Pc, pokemon: Pokemon) {
        showInputDialogGenero(poke, pokemon)
    }

    override fun onInputGeneroDone(poke: Pc, pokemon: Pokemon) {
        showInputDialogHabilidad(poke, pokemon)
    }

    override fun onInputHabilidadDone(poke: Pc, pokemon: Pokemon){
        val user = viewModel.getUser()
        user.pc!!.add(poke)

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

    override fun onInputStringDone(input: String) {
        TODO("Not yet implemented")
    }

}