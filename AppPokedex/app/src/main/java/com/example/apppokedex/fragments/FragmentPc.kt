package com.example.apppokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.example.apppokedex.entities.PokemonRepo

class FragmentPc : Fragment() {

    lateinit var vista : View
    lateinit var imgTitulo : ImageView
    lateinit var btnAddPokemon : Button
    lateinit var recPokemon : RecyclerView
    lateinit var adapter: PokemonAdapter

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    var pokemonRepository : PokemonRepo = PokemonRepo()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pc, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTituloPc)
        btnAddPokemon = vista.findViewById(R.id.btnAddPokemonPc)
        return vista

    }

    override fun onStart() {
        super.onStart()

        Glide.with(vista).load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png").into(imgTitulo)


        val idUser = activity?.intent?.getIntExtra("idUser", -1)

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()
        pokemonDao = db?.pokemonDao()
        pokemonUserDao = db?.pokemonUserDao()

        // Dummy call to pre-populate db
        if (idUser != null) {
            var user = userDao?.fetchUserById(idUser)
        }
        var pokemonList = pokemonDao?.fetchAllPokemon()
       // var pokemonUser = idUser?.let { pokemonUserDao?.fetchALLPokemonUserByIdUser(it) }

        btnAddPokemon.setOnClickListener{
            //crear nueva entrada a la base de datos y pasar la posicion como parametro
            val action = FragmentPcDirections.actionFragmentPcToFragmentPokemonData(0)
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }

        //Se debe levantar la base de datos del usuario con los pokemons cargados.
        // Esta base de datos solo la puede modificar el propio usuario y solo algunos parametros como el mote, peso y altura
        adapter = PokemonAdapter(pokemonList){ position ->
            val action = FragmentPcDirections.actionFragmentPcToFragmentPokemonData(
                pokemonDao?.fetchPokemonByIdPokemon(position)?.id ?: -1
            )
            findNavController().navigate(action)            //accion de cambiar de pantalla
//            Snackbar.make(vista, "Clik en ${pokemonRepository.pokemon[position].nombre}",Snackbar.LENGTH_SHORT)
        }
        recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
        recPokemon.adapter = adapter

        val swipeHandler = object : ActionLista(adapter) {
            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                // Permitir que se deslice solo en ciertos elementos del RecyclerView
                return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recPokemon)

    }

}