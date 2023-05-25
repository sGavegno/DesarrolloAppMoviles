package com.example.apppokedex.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.PokemonUserAdapter
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.PokemonUserDao
import com.example.apppokedex.entities.ActionListaPokemonUser
import com.example.apppokedex.entities.Pokemons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPc : Fragment(), PokemonUserAdapter.PokemonUserAdapterListener {

    private var db: AppDatabase? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    lateinit var vista : View
    lateinit var imgTitulo : ImageView
    lateinit var recPokemon : RecyclerView
    lateinit var adapter: PokemonUserAdapter

    lateinit var pokemom : Pokemons
    var pokemonList : MutableList<Pokemons?> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pc, container, false)
        recPokemon = vista.findViewById(R.id.listaPoxePc)
        imgTitulo = vista.findViewById(R.id.imgTituloPc)
        return vista

    }

    override fun onStart() {
        super.onStart()

        Glide.with(vista).load("https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png").into(imgTitulo)

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getString("UserID", "")
        val posPc = sharedPref?.getInt("pos_recycler_view_pc", 0)

        db = AppDatabase.getInstance(vista.context)
        pokemonDao = db?.pokemonDao()
        pokemonUserDao = db?.pokemonUserDao()

        if (idUser != null) {
            val userPokemon = pokemonUserDao?.fetchALLPokemonUserByIdUser(idUser)
            if (userPokemon != null) {
                for (user in userPokemon) {
                    pokemom = pokemonDao?.fetchPokemonByIdPokemon(user!!.idPokemon)!!
                    val pokemonFound = pokemonList.find { it?.idPokemon == pokemom.idPokemon }
                    if(pokemonFound == null)
                    {
                        pokemonList.add(pokemom)
                    }
                }
            }
        }

        adapter = PokemonUserAdapter(pokemonList, this)
        //recPokemon.layoutManager = LinearLayoutManager(context)       //da formato a la lista
        recPokemon.layoutManager = GridLayoutManager(context,2)
        recPokemon.scrollToPosition(posPc!!)
        recPokemon.adapter = adapter

        val swipeHandler = object : ActionListaPokemonUser(adapter, pokemonUserDao, pokemonList, idUser) {
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recPokemon)
    }

    //Funciones del adapter
    override fun onCardViewClick(pokemon: Pokemons, position: Int) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putInt("pos_recycler_view_pc", position)
                commit()
            }
        }
        val action = FragmentPcDirections.actionFragmentPcToFragmentPokemonData(
            pokemon.idPokemon
        )
        findNavController().navigate(action)            //accion de cambiar de pantalla
    }

}