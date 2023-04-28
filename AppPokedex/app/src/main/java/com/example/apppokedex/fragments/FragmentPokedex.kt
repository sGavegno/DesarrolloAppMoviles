package com.example.apppokedex.fragments

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
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.ActionLista
import com.example.apppokedex.entities.PokemonRepo

class FragmentPokedex : Fragment() {

    lateinit var vista : View
    lateinit var imgTitulo : ImageView
    lateinit var recPokemon : RecyclerView
    lateinit var adapter: PokemonAdapter

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null

    var pokemonRepository : PokemonRepo = PokemonRepo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTitulo)
        return vista
    }

    override fun onStart() {
        super.onStart()

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()
        pokemonDao = db?.PokemonDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()
        pokemonDao?.fetchAllUsers()

        //Acá se debe ingresar la base de datos de todos los pokemons, esta base de datos es Read Only. Modificar parametro del adapter para que reciba un PokemonDao
        adapter = PokemonAdapter(pokemonRepository.pokemon){ position ->
            val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(
                pokemonRepository.pokemon[position].id)
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }
        recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
        recPokemon.adapter = adapter

    }

}