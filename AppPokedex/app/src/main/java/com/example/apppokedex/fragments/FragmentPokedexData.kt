package com.example.apppokedex.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.EvolucionesAdapter
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.PokemonUserDao
import com.example.apppokedex.database.UserDao

class FragmentPokedexData : Fragment() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    lateinit var labelName : TextView
    lateinit var labelId : TextView
    lateinit var labelTipo : TextView
    lateinit var labelDebilidad : TextView
    lateinit var imgPokemon : ImageView
    lateinit var labelDescripcion : TextView
    lateinit var labelAltura : TextView
    lateinit var labelPeso : TextView
    lateinit var labelCategoria: TextView
    lateinit var labelHabilidad : TextView
    lateinit var txtEvolucion : TextView
    lateinit var recEvoluciones : RecyclerView
    lateinit var adapter: EvolucionesAdapter

    lateinit var vista : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex_data, container, false)
        labelName = vista.findViewById(R.id.txtPokedexName)
        labelId = vista.findViewById(R.id.txtPokedexIdDato)
        labelTipo = vista.findViewById(R.id.txtPokedexTipoDato)
        labelDebilidad = vista.findViewById(R.id.txtPokedexDebilidadDato)
        labelDescripcion = vista.findViewById(R.id.txtPokedexDescripcion)
        imgPokemon = vista.findViewById(R.id.imgPokedexDato)
        labelAltura = vista.findViewById(R.id.txtPokedexAlturaDato)
        labelPeso = vista.findViewById(R.id.txtPokedexPesoDato)
        labelCategoria = vista.findViewById(R.id.txtPokedexCategoriaDato)
        labelHabilidad = vista.findViewById(R.id.txtPokedexHabilidadDato)
        txtEvolucion = vista.findViewById(R.id.txtPokdexeEvolucion)
        recEvoluciones = vista.findViewById(R.id.listaPokedexEvolucion)

        return vista
    }

    override fun onStart() {
        super.onStart()

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()
        pokemonDao = db?.pokemonDao()
        pokemonUserDao = db?.pokemonUserDao()

        var PokemonEvolucionList: MutableList<Int> = mutableListOf()

        val idPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).idPokemon
        var pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)

        if(pokemon != null)
        {
            labelName.text = pokemon.nombre
            labelId.text =pokemon.idPokemon.toString()
            labelTipo.text =pokemon.tipo
            labelDebilidad.text = pokemon.debilidad
            labelDescripcion.text = pokemon.descripcion
            labelAltura.text = pokemon.altura
            labelPeso.text = pokemon.peso
            labelCategoria.text = pokemon.categoria
            labelHabilidad.text = pokemon.habilidad

            Glide.with(vista).load(pokemon.imgURL).into(imgPokemon)

            if(pokemon.child != 0) {
                var PokemonChild = pokemonDao?.fetchPokemonByIdPokemon(pokemon.child)
                if (PokemonChild != null && PokemonChild.child != 0) {
                    PokemonEvolucionList.add(PokemonChild.child)
                }
                PokemonEvolucionList.add(pokemon.child)
            }
            PokemonEvolucionList.add(pokemon.id)
            if(pokemon.parent != 0) {
                PokemonEvolucionList.add(pokemon.parent)
                var PokemonParent = pokemonDao?.fetchPokemonByIdPokemon(pokemon.parent)
                if (PokemonParent != null && PokemonParent.parent != 0) {
                    PokemonEvolucionList.add(PokemonParent.parent)
                }
            }

            adapter = EvolucionesAdapter(PokemonEvolucionList){ position ->
                //Guardar datos actualizados
                val idPokemon = PokemonEvolucionList[position]
                val action = FragmentPokedexDataDirections.actionFragmentPokedexDataSelf(
                    idPokemon)
                findNavController().navigate(action)            //accion de cambiar de pantalla
            }
            recEvoluciones.layoutManager = GridLayoutManager(context, PokemonEvolucionList.size)             //da formato a la lista
            recEvoluciones.adapter = adapter
        }
    }
}