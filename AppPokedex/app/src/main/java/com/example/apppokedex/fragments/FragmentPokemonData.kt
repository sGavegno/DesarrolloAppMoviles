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
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pokemons

class FragmentPokemonData : Fragment() {

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


    lateinit var pokemon : Pokemons

    lateinit var vista : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_pokemon_data, container, false)
        labelName = vista.findViewById(R.id.txtPokeName)
        labelId = vista.findViewById(R.id.txtPokeIdDato)
        labelTipo = vista.findViewById(R.id.txtPokeTipoDato)
        labelDebilidad = vista.findViewById(R.id.txtPokeDebilidadDato)
        labelDescripcion = vista.findViewById(R.id.txtPokeDescripcion)
        imgPokemon = vista.findViewById(R.id.imgPokeDato)
        labelAltura = vista.findViewById(R.id.txtPokeAlturaDato)
        labelPeso = vista.findViewById(R.id.txtPokePesoDato)
        labelCategoria = vista.findViewById(R.id.txtPokeCategoriaDato)
        labelHabilidad = vista.findViewById(R.id.txtPokeHabilidadDato)

        return vista
    }

    override fun onStart() {
        super.onStart()
//        findNavController().navigateUp()
        pokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).pokemonData

        labelName.text = pokemon.nombre
        labelId.text = pokemon.id.toString()
        labelTipo.text = pokemon.tipo
        labelDebilidad.text = pokemon.debilidad
        labelDescripcion.text = pokemon.descripcion
        labelAltura.text = pokemon.altura
        labelPeso.text = pokemon.peso
        labelCategoria.text = pokemon.categoria
        labelHabilidad.text = pokemon.habilidad
        Glide.with(vista).load(pokemon.imgURL).into(imgPokemon)

    }
}