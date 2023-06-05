package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.EvolucionesAdapter
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonTipo
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class FragmentPokedexData : Fragment() {

    private val viewModel: FragmentPokedexDataViewModel by viewModels()

    private lateinit var labelName : TextView
    private lateinit var labelId : TextView
    private lateinit var labelGeneracion : TextView
    private lateinit var labelDebilidad : TextView
    private lateinit var imgPokemon : ImageView
    private lateinit var labelDescripcion : TextView
    private lateinit var labelAltura : TextView
    private lateinit var labelPeso : TextView
    private lateinit var labelHabilidad : TextView
    private lateinit var txtEvolucion : TextView
    private lateinit var recEvoluciones : RecyclerView
    private lateinit var adapter: EvolucionesAdapter

    private lateinit var imgTipo1 : ImageView
    private lateinit var imgTipo2 : ImageView

    lateinit var vista : View
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex_data, container, false)
        labelName = vista.findViewById(R.id.txtPokedexName)
        labelId = vista.findViewById(R.id.txtPokedexIdDato)
        imgTipo1 = vista.findViewById(R.id.imgTipo1)
        imgTipo2 = vista.findViewById(R.id.imgTipo2)
        labelGeneracion = vista.findViewById(R.id.txtPokedexGeneracionDato)
        labelDebilidad = vista.findViewById(R.id.txtPokedexDebilidadDato)
        labelDescripcion = vista.findViewById(R.id.txtPokedexDescripcion)
        imgPokemon = vista.findViewById(R.id.imgPokedexDato)
        labelAltura = vista.findViewById(R.id.txtPokedexAlturaDato)
        labelPeso = vista.findViewById(R.id.txtPokedexPesoDato)
        labelHabilidad = vista.findViewById(R.id.txtPokedexHabilidadDato)
        txtEvolucion = vista.findViewById(R.id.txtPokdexeEvolucion)
        recEvoluciones = vista.findViewById(R.id.listaPokedexEvolucion)

        viewModel.statePokemon.observe(viewLifecycleOwner){
            when (it) {
                State.LOADING -> {
                    Snackbar.make(vista, "Procesando Datos", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS -> {
                    val pokemon = viewModel.getPokemon()
                    setPokemonData(pokemon)
                }
                State.FAILURE -> {
                    Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }

        }

        viewModel.stateEvolucion.observe(viewLifecycleOwner){
            when (it) {
                State.LOADING -> {
                    Snackbar.make(vista, "Procesando Evoluciones", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS -> {
                    val evolucionesLista = viewModel.getEvoluciones().cadenaEvolutiva
                    if(evolucionesLista != null){
                        adapter = EvolucionesAdapter(evolucionesLista)
                        val layoutManager = LinearLayoutManager(context)
                        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                        recEvoluciones.layoutManager = layoutManager
                        //recEvoluciones.layoutManager = GridLayoutManager(context, PokemonEvolucionList.size)             //da formato a la lista
                        recEvoluciones.adapter = adapter
                    }
                }
                State.FAILURE -> {
                    Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.stateTablaTipo.observe(viewLifecycleOwner){
            when (it) {
                State.LOADING -> {
                    Snackbar.make(vista, "Procesando Evoluciones", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS -> {
                    Snackbar.make(vista, "Tipos Cargaos", Snackbar.LENGTH_SHORT).show()
                    val tiposPokemon = viewModel.getTablaTiposPokemon().tipos
                    val pokemon = viewModel.getPokemon()
                    //Buscar los tipos que coincidan con el tipo del pokemon
                    labelDebilidad.text = "Implementar Tabla PokemonTipos"
                }
                State.FAILURE -> {
                    Snackbar.make(vista, "Fallo la carga", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        return vista
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        val idPokemon = FragmentPokedexDataArgs.fromBundle(requireArguments()).idPokemon
        viewModel.getPokemonById(idPokemon)

    }

    @SuppressLint("SetTextI18n")
    private fun setPokemonData(pokemon: Pokemon){
        val auxTipo :MutableList<PokemonTipo> = mutableListOf()

        labelName.text = pokemon.nombre!!.uppercase(Locale.getDefault())
        labelId.text = pokemon.id.toString()
        var cont = 0
        for(tipo in pokemon.tipo!!){
            tipo.idTipo?.let { it1 -> setImgTipo(it1, cont) }
            auxTipo.add(tipo)
            cont += 1
        }

        labelGeneracion.text = pokemon.detalle?.idGeneracion.toString()
        labelDebilidad.text = "Implementar Tabla PokemonTipos"
        labelDescripcion.text = "pokemon.descripcion"
        val altura = pokemon.altura
        val alturaString = (altura?.div(10)).toString() + "," + (altura?.rem(10)).toString() + " m"
        labelAltura.text = alturaString
        val peso = pokemon.peso
        val pesoString = (peso?.div(10)).toString() + "," + (peso?.rem(10)).toString() + " kg"
        labelPeso.text = pesoString

        var habilidadString = ""
        for(habilidad in pokemon.habilidades!!){
            habilidadString += (habilidad.detalle?.nombre ?: "Habilidad")
            habilidadString += "\n"
        }
        labelHabilidad.text = habilidadString

        val id = pokemon.id
        if(id != null){
            if(id < 10){
                Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${pokemon.id}.png").into(imgPokemon)
                imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${pokemon.id}.png"
            }else if(id < 100){
                Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${pokemon.id}.png").into(imgPokemon)
                imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${pokemon.id}.png"
            }else{
                Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokemon.id}.png").into(imgPokemon)
                imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokemon.id}.png"
            }
        }
        pokemon.detalle?.idCadenaEvolutiva?.let { it1 -> viewModel.getEvolucionesById(it1) }
    }

    private fun setImgTipo(idTipo : Int, imgN: Int){
        val imgTipo : ImageView = if(imgN == 0) {
            imgTipo1
        } else {
            imgTipo2
        }

        when(idTipo){
            1->{
                Glide.with(vista).load(R.drawable.tipo_normal).into(imgTipo)
            }
            2->{
                Glide.with(vista).load(R.drawable.tipo_lucha).into(imgTipo)
            }
            3->{
                Glide.with(vista).load(R.drawable.tipo_volador).into(imgTipo)
            }
            4->{
                Glide.with(vista).load(R.drawable.tipo_veneno).into(imgTipo)
            }
            5->{
                Glide.with(vista).load(R.drawable.tipo_tierra).into(imgTipo)
            }
            6->{
                Glide.with(vista).load(R.drawable.tipo_roca).into(imgTipo)
            }
            7->{
                Glide.with(vista).load(R.drawable.tipo_bicho).into(imgTipo)
            }
            8->{
                Glide.with(vista).load(R.drawable.tipo_fantasma).into(imgTipo)
            }
            9->{
                Glide.with(vista).load(R.drawable.tipo_acero).into(imgTipo)
            }
            10->{
                Glide.with(vista).load(R.drawable.tipo_fuego).into(imgTipo)
            }
            11->{
                Glide.with(vista).load(R.drawable.tipo_agua).into(imgTipo)
            }
            12->{
                Glide.with(vista).load(R.drawable.tipo_planta).into(imgTipo)
            }
            13->{
                Glide.with(vista).load(R.drawable.tipo_electrico).into(imgTipo)
            }
            14->{
                Glide.with(vista).load(R.drawable.tipo_psiquico).into(imgTipo)
            }
            15->{
                Glide.with(vista).load(R.drawable.tipo_hielo).into(imgTipo)
            }
            16->{
                Glide.with(vista).load(R.drawable.tipo_dragon).into(imgTipo)
            }
            17->{
                Glide.with(vista).load(R.drawable.tipo_siniestro).into(imgTipo)
            }
            18->{
                Glide.with(vista).load(R.drawable.tipo_hada).into(imgTipo)
            }
        }
    }

}
