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
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
                //showAlertDialogAddPokemon(pokemonDao)
//                val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(0)
//                findNavController().navigate(action)            //accion de cambiar de pantalla
            }

            val swipeHandler = object : ActionLista(adapter, pokemonDao, pokemonList) {
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recPokemon)
        } else {
            btnPokemdexAdd.isInvisible
        }
    }

    private fun setInputDialogListener(listener: InputDialogListener) {
        this.inputDialogListener = listener
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
    private fun onClick() {
        showInputDialog()
    }

    override fun onCardViewClick(item: Int) {
        // Código para atender el clic del CardView del elemento del RecyclerView
        val pokemonList = pokemonDao?.fetchAllPokemon()
        val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(
            pokemonList?.get(item)?.idPokemon ?: 0
        )
        findNavController().navigate(action)            //accion de cambiar de pantalla
    }
    override fun onButtonClick(item: Pokemons) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)
        val checkPokemonUser = pokemonUserDao?.fetchPokemonUserByPokemon(idUser!!, item.idPokemon)
        if(checkPokemonUser == null)
        {
            pokemonUserDao?.insertPokemonUser(PokemonUser(0,idUser!!,item.idPokemon,item.nombre,0,item.altura,item.peso,item.descripcion))
            Snackbar.make(vista, "${item.nombre} ha sido agregado correctamente", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(vista, "${item.nombre} ya esa en la lista.", Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            "currentPosition",
            (recPokemon.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        )
    }

}
