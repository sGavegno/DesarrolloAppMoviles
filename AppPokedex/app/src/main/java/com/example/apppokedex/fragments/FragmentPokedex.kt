package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.activity.MainActivity
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.database.*
import com.example.apppokedex.entities.ActionLista
import com.example.apppokedex.entities.PokemonUser
import com.example.apppokedex.entities.Pokemons
import com.google.android.material.snackbar.Snackbar

class FragmentPokedex : Fragment(), PokemonAdapter.PokemonAdapterListener,InputDialogListener {
    private lateinit var imgTitulo : ImageView
    private lateinit var btnLogOut : Button
    private lateinit var btnPokemdexAdd : Button
    private lateinit var recPokemon : RecyclerView
    private lateinit var adapter: PokemonAdapter

    lateinit var vista : View

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    private var inputDialogListener: InputDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTitulo)
        btnLogOut = vista.findViewById(R.id.btnPokedexLogOut)
        btnPokemdexAdd = vista.findViewById(R.id.btnPokedexAdd)

        return vista
    }

    override fun onStart() {
        super.onStart()
        setInputDialogListener(this)
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)
        val posPokedex = sharedPref?.getInt("pos_recycler_view_pokedex", 0)


        Glide.with(vista).load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png").into(imgTitulo)

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()
        pokemonDao = db?.pokemonDao()
        pokemonUserDao = db?.pokemonUserDao()

        pokemonDao?.fetchAllPokemon()
        pokemonUserDao?.fetchAllPokemonUser()

        // Dummy call to pre-populate db
        val user = userDao?.fetchUserById(idUser)

        val pokemonList = pokemonDao?.fetchAllPokemon()

        adapter = PokemonAdapter(pokemonList, this)
        recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
        //recPokemon.layoutManager = GridLayoutManager(context,3)
        recPokemon.scrollToPosition(posPokedex!!)
        recPokemon.adapter = adapter

        btnLogOut.setOnClickListener{
            //Funcion para deslogearce
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        if(user!!.permisos == 1){
            // Solo acciones para usuarios con permiso de supervisor Falta agregar el boton para incluir pokemons
            btnPokemdexAdd.setOnClickListener{
                onClick()
            }
            val swipeHandler = object : ActionLista(adapter, pokemonDao, pokemonList) {
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recPokemon)
        } else {
            btnPokemdexAdd.visibility = View.INVISIBLE
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
        val pokemon = pokemonDao?.fetchPokemonByIdPokemon(input)
        if(pokemon == null)
        {
            pokemonDao?.insertPokemon(newPokemon)
            val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(input)
            findNavController().navigate(action)            //accion de cambiar de pantalla
        } else {
            Snackbar.make(vista, "El Id: $input ya se encuentra en la base de datos", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onInputStringDone(input: String) {
        TODO("Not yet implemented")
    }

    //Funciones del Adapter
    override fun onCardViewClick(pokemon: Pokemons, position: Int) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putInt("pos_recycler_view_pokedex", position)
                commit()
            }
        }
        // Código para atender el clic del CardView del elemento del RecyclerView
        val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(
            pokemon.idPokemon
        )
        findNavController().navigate(action)            //accion de cambiar de pantalla
    }
    override fun onButtonClick(pokemon: Pokemons) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)
        val checkPokemonUser = pokemonUserDao?.fetchPokemonUserByPokemon(idUser!!, pokemon.idPokemon)
        if(checkPokemonUser == null)
        {
            pokemonUserDao?.insertPokemonUser(PokemonUser(0,idUser!!,pokemon.idPokemon,pokemon.nombre,0,pokemon.altura,pokemon.peso,pokemon.descripcion))
            Snackbar.make(vista, "${pokemon.nombre} ha sido agregado correctamente", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(vista, "${pokemon.nombre} ya esa en la lista.", Snackbar.LENGTH_SHORT).show()
        }
    }

}
