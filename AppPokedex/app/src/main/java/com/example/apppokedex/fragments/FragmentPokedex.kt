package com.example.apppokedex.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.PokemonUserDao
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.ActionLista
import com.example.apppokedex.entities.PokemonUser
import com.example.apppokedex.entities.Pokemons

class FragmentPokedex : Fragment(), PokemonAdapter.PokemonAdapterListener {

    lateinit var vista : View
    private lateinit var imgTitulo : ImageView
    private lateinit var recPokemon : RecyclerView
    private lateinit var adapter: PokemonAdapter

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTitulo)
        return vista
    }

    override fun onStart() {
        super.onStart()

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

        if(user!!.permisos == 1){       // Solo acciones para usuarios con permismo de supervisor Falta agregar el boton para incluir pokemons
            val swipeHandler = object : ActionLista(adapter, pokemonUserDao, pokemonList, idUser) {
                override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                    // Permitir que se deslice solo en ciertos elementos del RecyclerView
                    return super.getSwipeDirs(recyclerView, viewHolder)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recPokemon)
        }
    }

    override fun onCardViewClick(item: Int) {
        // Código para atender el clic del CardView del elemento del RecyclerView
        val pokemonList = pokemonDao?.fetchAllPokemon()
        val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(
            pokemonList?.get(item)?.idPokemon ?: -1
        )
        findNavController().navigate(action)            //accion de cambiar de pantalla
    }
    override fun onButtonClick(item: Pokemons) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)
        pokemonUserDao?.insertPokemonUser(PokemonUser(0,idUser!!,item.idPokemon,item.nombre,0,item.altura,item.peso,item.descripcion))
//        Snackbar.make(vista, "Has clickeado en el elemento: ${item.nombre}", Snackbar.LENGTH_SHORT).show()
    }

}