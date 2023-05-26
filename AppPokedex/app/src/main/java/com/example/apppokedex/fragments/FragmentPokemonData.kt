package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.PokemonUserDao
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPokemonData : Fragment() {

    val viewModel: FragmentPokemonDataViewModel by viewModels()

    private var db: AppDatabase? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    private lateinit var imgPokemon : ImageView
    private lateinit var labelLvl : TextView
    private lateinit var labelMote : TextView
    private lateinit var labelGenero : TextView
    private lateinit var btnLevlUp : Button
    private lateinit var btnAddObjeto : Button
    private lateinit var labelId : TextView
    private lateinit var labelName : TextView
    private lateinit var imgTipo1 : ImageView
    private lateinit var imgTipo2 : ImageView
    private lateinit var labelHabilidad : TextView
    private lateinit var labelObjeto : TextView
    private lateinit var labelNotas : TextView

    private lateinit var labelPs : TextView
    private lateinit var barrPs : ProgressBar
    private lateinit var labelAtaque : TextView
    private lateinit var barrAtaque : ProgressBar
    private lateinit var labelDefensa : TextView
    private lateinit var barrDefensa : ProgressBar
    private lateinit var labelAtEsp : TextView
    private lateinit var barrAtEsp : ProgressBar
    private lateinit var labelDefEsp : TextView
    private lateinit var barrDefEsp : ProgressBar
    private lateinit var labelVelocidad : TextView
    private lateinit var barrVelocidad : ProgressBar

    lateinit var vista : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokemon_data, container, false)

        imgPokemon = vista.findViewById(R.id.imgPokeDato)
        labelLvl = vista.findViewById(R.id.txtPokeLvlDato)
        labelMote = vista.findViewById(R.id.txtPokeMoteDato)
        labelGenero = vista.findViewById(R.id.txtGeneroDato)
        btnLevlUp = vista.findViewById(R.id.btnLvlUp)
        btnAddObjeto = vista.findViewById(R.id.btnObjeto)
        labelId = vista.findViewById(R.id.txtPokeIdDato)
        labelName = vista.findViewById(R.id.txtPokeNameDato)
        imgTipo1 = vista.findViewById(R.id.imgPokemonTipo1)
        imgTipo2 = vista.findViewById(R.id.imgPokemonTipo2)
        labelHabilidad = vista.findViewById(R.id.txtPokeHabilidadDato)
        labelObjeto = vista.findViewById(R.id.txtPokeObjetoDato)
        labelNotas = vista.findViewById(R.id.txtNotas)

        labelPs = vista.findViewById(R.id.txtPsDato)
        barrPs = vista.findViewById(R.id.progressBarPs)
        labelAtaque = vista.findViewById(R.id.txtAtaqueDato)
        barrAtaque = vista.findViewById(R.id.progressBarAtaque)
        labelDefensa = vista.findViewById(R.id.txtDefensaDato)
        barrDefensa = vista.findViewById(R.id.progressBarDefensa)
        labelAtEsp = vista.findViewById(R.id.txtAtEspDato)
        barrAtEsp = vista.findViewById(R.id.progressBarAtEsp)
        labelDefEsp = vista.findViewById(R.id.txtDefEspDato)
        barrDefEsp = vista.findViewById(R.id.progressBarDefEsp)
        labelVelocidad = vista.findViewById(R.id.txtVelocidadDato)
        barrVelocidad = vista.findViewById(R.id.progressBarVelocidad)

        return vista
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        val idUser = viewModel.getIdUser()

        db = AppDatabase.getInstance(vista.context)
        pokemonDao = db?.pokemonDao()
        pokemonUserDao = db?.pokemonUserDao()

        val idPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).idPokemon

        viewModel.getPokemonById(idPokemon)
        viewModel.getPokemonUserById(idUser, idPokemon)

        viewModel.pokemonUserData.observe(this){
/*
            labelLvl.text = pokemonUser.nivel.toString()
            labelMote.text = pokemonUser.mote
            if(true){
                labelGenero.text = "♂"
            }else{
                labelGenero.text = "♀"
            }
*/
        }

        viewModel.pokemonData.observe(this){

            labelId.text = it.Id.toString()
            labelName.text = it.Nombre
            var cont = 0
            for(tipo in it.Tipo!!){
                tipo.Id_Tipo?.let { it1 -> setImgTipo(it1, cont) }
                cont += 1
            }

            var habilidadString = ""
            for(habilidad in it.Habilidades!!){
                habilidadString += (habilidad.Detalle?.Nombre ?: "Habilidad")
                habilidadString += "\n"
            }
            labelHabilidad.text = habilidadString

            labelNotas.text = "Test. Se captuo en... al nivle ##"

            //
            for(state in it.Stats!!){
                state.Detalle?.Nombre?.let { it1 ->
                    state.Stats_Base?.let { it2 ->
                        setStats(it1,it2)
                    }
                }
            }

            val id = it.Id
            if(id != null){
                if(id < 10){
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00"+ it.Id +".png").into(imgPokemon)
                    imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00"+ it.Id +".png"
                }else if(id < 100){
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0"+ it.Id +".png").into(imgPokemon)
                    imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0"+ it.Id +".png"
                }else{
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"+ it.Id +".png").into(imgPokemon)
                    imgPokemon.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"+ it.Id +".png"
                }
            }
        }

        btnAddObjeto.setOnClickListener {

        }
        btnLevlUp.setOnClickListener {

        }
    }

    private fun setStats(stat:String, datoStat:Int){

        when(stat){
            "Ps" ->{
                labelPs.text = datoStat.toString()
                barrPs.progress = datoStat
            }
            "Ataque" ->{
                labelAtaque.text = datoStat.toString()
                barrAtaque.progress = datoStat
            }
            "Defensa" ->{
                labelDefensa.text = datoStat.toString()
                barrDefensa.progress = datoStat
            }
            "AtEsp" ->{
                labelAtEsp.text = datoStat.toString()
                barrAtEsp.progress = datoStat
            }
            "DefEsp" ->{
                labelDefEsp.text = datoStat.toString()
                barrDefEsp.progress = datoStat
            }
            "Velocidad" ->{
                labelVelocidad.text = datoStat.toString()
                barrVelocidad.progress = datoStat
            }
        }
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
