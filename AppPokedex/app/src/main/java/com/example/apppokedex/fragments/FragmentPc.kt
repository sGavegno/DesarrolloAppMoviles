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

            var idPokemon = 0
            viewModel.pokemonData.observe(this) {

//                onClick()

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
                        true,
                        "",
                        "",
                        "",
                        ""
                    )
                    onClick(pokemonPc)
                    user.pc!!.add(pokemonPc)
                }
                val pokedexUser = user.pokedex
                if (pokedexUser != null) {
                    val pokedexAux = pokedexUser.filter { item -> item.idPokemon == it.id }
                    if (pokedexAux.isEmpty()) {
                        val pokedex = UserPokedex(
                            it.id,
                            it.nombre,
                            it.tipo
                        )
                        user.pokedex!!.add(pokedex)
                    }
                }
                viewModel.updateUserData(user)
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
    private fun onClick(poke: Pc) {
        //showInputDialog(poke)
        showInputDialogMote(poke)
        //showInputDialogNivel(poke)
        //showInputDialogGenero(poke)
    }

    private fun showInputDialog(poke: Pc) {
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

    private fun showInputDialogMote(poke: Pc) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese Mote del Pokemon")
        val input = EditText(requireContext())
        alertDialog.setView(input)
        alertDialog.setPositiveButton("Confirmar") { _, _ ->
            poke.mote = input.text.toString()
            inputDialogListener?.onInputMoteDone(poke)
        }
        alertDialog.setNegativeButton("Cancelar") { _, _ ->
            inputDialogListener?.onInputMoteDone(poke)
//            dialog.cancel()
        }
        alertDialog.show()
    }

    private fun showInputDialogNivel(poke: Pc) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese el ID del Pokemon")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.keyListener =
            DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9
        alertDialog.setView(input)
        alertDialog.setPositiveButton("OK") { _, _ ->
            poke.nivel = input.text.toString().toInt()
            inputDialogListener?.onInputNivelDone(poke)
        }
        alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }

    private fun showInputDialogGenero(poke: Pc) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Ingrese el ID del Pokemon")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        alertDialog.setView(input)
        alertDialog.setPositiveButton("OK") { _, _ ->
//            poke.genero = input.text.toString().toInt()
            inputDialogListener?.onInputGeneroDone(poke)
        }
        alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
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

    override fun onInputMoteDone(poke: Pc) {
        showInputDialogNivel(poke)
    }

    override fun onInputNivelDone(poke: Pc) {
        showInputDialogGenero(poke)
    }

    override fun onInputGeneroDone(poke: Pc) {
        TODO("Not yet implemented")
    }

    override fun onInputStringDone(input: String) {
        TODO("Not yet implemented")
    }

}