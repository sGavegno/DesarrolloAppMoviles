package com.example.apppokedex.fragments

import androidx.lifecycle.ViewModelProvider
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
import com.example.apppokedex.entities.PokemonRepo
import com.example.apppokedex.entities.Pokemons
import com.google.android.material.snackbar.Snackbar

class FragmentPokedexData : Fragment() {
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

    lateinit var pokemon : Pokemons

    var pokemonRepository : PokemonRepo = PokemonRepo()

    lateinit var vista : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        txtEvolucion = vista.findViewById(R.id.txtPokeEvolucion)
        recEvoluciones = vista.findViewById(R.id.listaPokedexEvolucion)

        return vista
    }

    override fun onStart() {
        super.onStart()
//        findNavController().navigateUp()
        lateinit var PokemonEvolucionList: List<Int>
        var idPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).idPokemon
        pokemon = pokemonRepository.pokemon[idPokemon-1]

        if(pokemon.nombre != "")
        {
            labelName.text = pokemon.nombre
            labelId.text =pokemon.id.toString()
            labelTipo.text =pokemon.tipo
            labelDebilidad.text = pokemon.debilidad
            labelDescripcion.text = pokemon.descripcion
            labelAltura.text = pokemon.altura
            labelPeso.text = pokemon.peso
            labelCategoria.text = pokemon.categoria
            labelHabilidad.text = pokemon.habilidad

            Glide.with(vista).load(pokemon.imgURL).into(imgPokemon)

            if(pokemon.child != 0) {
                PokemonEvolucionList = PokemonEvolucionList.plus(pokemon.child)
            }
            PokemonEvolucionList = PokemonEvolucionList.plus(pokemon.id)
            if(pokemon.parent != 0) {
                PokemonEvolucionList = PokemonEvolucionList.plus(pokemon.parent)
            }

            adapter = EvolucionesAdapter(PokemonEvolucionList){ position ->
//          onItemClick( ) cambiar a la pantalla datos
                //Guardar datos actualizados
                Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
                val action = FragmentPokemonDataDirections.actionFragmentPokemonDataSelf(
                    PokemonEvolucionList[position])
                findNavController().navigate(action)            //accion de cambiar de pantalla
            }
            recEvoluciones.layoutManager = GridLayoutManager(context, PokemonEvolucionList.size)             //da formato a la lista
            recEvoluciones.adapter = adapter
        }
    }
}