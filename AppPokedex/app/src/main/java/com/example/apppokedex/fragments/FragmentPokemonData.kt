package com.example.apppokedex.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.EvolucionesAdapter
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.entities.PokemonRepo
import com.example.apppokedex.entities.Pokemons
import com.google.android.material.snackbar.Snackbar

class FragmentPokemonData : Fragment() {

    lateinit var labelName : EditText
    lateinit var labelId : EditText
    lateinit var labelTipo : EditText
    lateinit var labelDebilidad : EditText
    lateinit var imgPokemon : ImageView
    lateinit var labelDescripcion : EditText
    lateinit var labelAltura : EditText
    lateinit var labelPeso : EditText
    lateinit var labelCategoria: EditText
    lateinit var labelHabilidad : EditText

    lateinit var recEvoluciones : RecyclerView
    lateinit var adapter: EvolucionesAdapter

    lateinit var pokemon : Pokemons
    var pokemonRepository : PokemonRepo = PokemonRepo()

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

        recEvoluciones = vista.findViewById(R.id.listaEvolucion)

        return vista
    }

    override fun onStart() {
        super.onStart()
//        findNavController().navigateUp()
        pokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).pokemonData

        labelName.setText(pokemon.nombre)
        labelId.setText(pokemon.id.toString())
        labelTipo.setText(pokemon.tipo)
        labelDebilidad.setText(pokemon.debilidad)
        labelDescripcion.setText(pokemon.descripcion)
        labelAltura.setText(pokemon.altura)
        labelPeso.setText(pokemon.peso)
        labelCategoria.setText(pokemon.categoria)
        labelHabilidad.setText(pokemon.habilidad)

        Glide.with(vista).load(pokemon.imgURL).into(imgPokemon)

        adapter = EvolucionesAdapter(pokemon.evolucion){ position ->
//          onItemClick( ) cambiar a la pantalla datos
            //Guardar datos actualizados
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
            val action = FragmentPokemonDataDirections.actionFragmentPokemonDataSelf(
                pokemonRepository.pokemon[pokemon.evolucion[position]-1])
            findNavController().navigate(action)            //accion de cambiar de pantalla
//            Snackbar.make(vista, "Clik en ${pokemonRepository.pokemon[position].nombre}",Snackbar.LENGTH_SHORT)
        }
        recEvoluciones.layoutManager = GridLayoutManager(context,3)             //da formato a la lista
        recEvoluciones.adapter = adapter

    }

    override fun onStop() {
        super.onStop()
        //Atender accion de ir para atras, y guardar cambios
        Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
    }

}