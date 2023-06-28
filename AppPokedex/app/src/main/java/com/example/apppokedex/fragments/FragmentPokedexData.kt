package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.EvolucionesAdapter
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonTipo
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.TablaTiposPokemon
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class FragmentPokedexData : Fragment() {

    private val viewModel: FragmentPokedexDataViewModel by viewModels()

    private lateinit var labelName : TextView
    private lateinit var labelId : TextView
    private lateinit var labelGeneracion : TextView
    private lateinit var imgPokemon : ImageView
    private lateinit var labelAltura : TextView
    private lateinit var labelPeso : TextView
    private lateinit var progresBarr: ProgressBar
    private lateinit var scrollView: NestedScrollView
    private lateinit var clPokemonDato: ConstraintLayout

    private var imgTipo : MutableList<ImageView> = mutableListOf()
    private var imgTipoDevilidad : MutableList<ImageView> = mutableListOf()
    private var imgTipoEfectivo : MutableList<ImageView> = mutableListOf()
    private var imgTipoNoEfectivo : MutableList<ImageView> = mutableListOf()
    private var imgTipoInmune : MutableList<ImageView> = mutableListOf()

    lateinit var vista : View
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex_data, container, false)
        labelName = vista.findViewById(R.id.txtPokedexName)
        labelId = vista.findViewById(R.id.txtPokedexIdDato)
        labelGeneracion = vista.findViewById(R.id.txtPokedexGeneracionDato)
        imgPokemon = vista.findViewById(R.id.imgPokedexDato)
        labelAltura = vista.findViewById(R.id.txtPokedexAlturaDato)
        labelPeso = vista.findViewById(R.id.txtPokedexPesoDato)
        progresBarr = vista.findViewById(R.id.progressBarPokedexDato)
        scrollView = vista.findViewById(R.id.scrollViewPokedexDato)
        clPokemonDato = vista.findViewById(R.id.clPokemonDato)

        imgTipo.add(vista.findViewById(R.id.imgTipo1))
        imgTipo.add(vista.findViewById(R.id.imgTipo2))

        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad1))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad2))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad3))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad4))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad5))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad6))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad7))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad8))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad9))
        imgTipoDevilidad.add(vista.findViewById(R.id.imgTipoDebilidad10))

        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo1))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo2))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo3))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo4))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo5))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo6))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo7))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo8))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo9))
        imgTipoEfectivo.add(vista.findViewById(R.id.imgTipoEfectivo10))

        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo1))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo2))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo3))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo4))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo5))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo6))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo7))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo8))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo9))
        imgTipoNoEfectivo.add(vista.findViewById(R.id.imgTipoNoEfectivo10))

        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune1))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune2))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune3))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune4))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune5))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune6))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune7))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune8))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune9))
        imgTipoInmune.add(vista.findViewById(R.id.imgTipoInmune10))

        viewModel.statePokemon.observe(viewLifecycleOwner){
            when (it) {
                State.LOADING -> {
                    labelName.visibility = View.INVISIBLE
                    labelId.visibility = View.INVISIBLE
                    labelGeneracion.visibility = View.INVISIBLE
                    clPokemonDato.visibility = View.INVISIBLE
                    scrollView.visibility = View.INVISIBLE
                    progresBarr.visibility = View.VISIBLE
                }
                State.SUCCESS -> {
                    val pokemon = viewModel.getPokemon()
                    setPokemonData(pokemon)

                    progresBarr.visibility = View.INVISIBLE
                    scrollView.visibility = View.VISIBLE
                    labelName.visibility = View.VISIBLE
                    labelId.visibility = View.VISIBLE
                    labelGeneracion.visibility = View.VISIBLE
                    clPokemonDato.visibility = View.VISIBLE
                }
                State.FAILURE -> {
                    progresBarr.visibility = View.INVISIBLE
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
        var auxTipoDebilidad :MutableList<Int> = mutableListOf()
        var auxTipoEfectivo :MutableList<Int> = mutableListOf()
        var auxTipoNoEfectivo :MutableList<Int> = mutableListOf()
        var auxTipoInmune :MutableList<Int> = mutableListOf()

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

        labelName.text = pokemon.nombre!!.uppercase(Locale.getDefault())
        labelId.text = pokemon.id.toString()

        var cont = 0
        for(tipo in pokemon.tipo!!){
            tipo.idTipo?.let { it1 -> setImgTipo(it1, imgTipo[cont]) }
            auxTipo.add(tipo)
            cont += 1
        }

        auxTipoDebilidad = viewModel.analizarDebilidad(auxTipo)
        cont = 0
        for(tipo in auxTipoDebilidad){
            setImgTipo(tipo, imgTipoDevilidad[cont])
            cont += 1
        }

        auxTipoEfectivo = viewModel.analizarEfectividad(auxTipo)
        cont = 0
        for(tipo in auxTipoEfectivo){
            setImgTipo(tipo, imgTipoEfectivo[cont])
            cont += 1
        }

        auxTipoNoEfectivo = viewModel.analizarNoEfectivo(auxTipo)
        cont = 0
        for(tipo in auxTipoNoEfectivo){
            setImgTipo(tipo, imgTipoNoEfectivo[cont])
            cont += 1
        }

        auxTipoInmune = viewModel.analizarInmune(auxTipo)
        cont = 0
        for(tipo in auxTipoInmune){
            setImgTipo(tipo, imgTipoInmune[cont])
            cont += 1
        }

        labelGeneracion.text = pokemon.detalle?.idGeneracion.toString()

        val altura = pokemon.altura
        val alturaString = (altura?.div(10)).toString() + "," + (altura?.rem(10)).toString() + " m"
        labelAltura.text = alturaString
        val peso = pokemon.peso
        val pesoString = (peso?.div(10)).toString() + "," + (peso?.rem(10)).toString() + " kg"
        labelPeso.text = pesoString

    }

    private fun setImgTipo(idTipo : Int, imgTipo: ImageView){
        imgTipo.visibility = View.VISIBLE
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
