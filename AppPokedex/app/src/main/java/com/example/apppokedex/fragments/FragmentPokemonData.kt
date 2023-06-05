package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class FragmentPokemonData : Fragment() {

    private val viewModel: FragmentPokemonDataViewModel by viewModels()

    private lateinit var imgPokemon : ImageView
    private lateinit var labelLvl : TextView
    private lateinit var labelMote : TextView
    private lateinit var labelGenero : TextView
    private lateinit var btnLiberar : Button
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
    private lateinit var progressBarLouding : ProgressBar
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchDatos: Switch

    private lateinit var constLayaut: ConstraintLayout
    private lateinit var clDatos: ConstraintLayout
    private lateinit var clStats: ConstraintLayout

    lateinit var vista : View

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokemon_data, container, false)

        constLayaut=vista.findViewById(R.id.fragmentPokemon)
        progressBarLouding = vista.findViewById(R.id.progressBarPokemonData)

        imgPokemon = vista.findViewById(R.id.imgPokeDato)
        labelLvl = vista.findViewById(R.id.txtPokeLvlDato)
        labelMote = vista.findViewById(R.id.txtPokeMoteDato)
        labelGenero = vista.findViewById(R.id.txtGeneroDato)
        btnLiberar = vista.findViewById(R.id.btnLiberar)
        btnLevlUp = vista.findViewById(R.id.btnLvlUp)
        btnAddObjeto = vista.findViewById(R.id.btnObjeto)
        labelId = vista.findViewById(R.id.txtPokeIdDato)
        labelName = vista.findViewById(R.id.txtPokeNameDato)
        imgTipo1 = vista.findViewById(R.id.imgPokemonTipo1)
        imgTipo2 = vista.findViewById(R.id.imgPokemonTipo2)
        labelHabilidad = vista.findViewById(R.id.txtPokeHabilidadDato)
        labelObjeto = vista.findViewById(R.id.txtPokeObjetoDato)
        labelNotas = vista.findViewById(R.id.txtNotasDato)

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

        switchDatos = vista.findViewById(R.id.swDatos)
        clDatos = vista.findViewById(R.id.clPokemonInfo)
        clStats = vista.findViewById(R.id.clPokemonStats)

        val animation = ScaleAnimation(
            0.5f, 1.5f, // Factor de escala en el eje X (desde 1 hasta 1.5)
            0.5f, 1.5f, // Factor de escala en el eje Y (desde 1 hasta 1.5)
            Animation.RELATIVE_TO_SELF, 0.5f, // Punto pivote para el escalado en el eje X (50% del ancho de la imagen)
            Animation.RELATIVE_TO_SELF, 0.5f // Punto pivote para el escalado en el eje Y (50% de la altura de la imagen)
        ).apply {
            duration = 1000 // Duración de la animación (en milisegundos)
            repeatCount = Animation.INFINITE // Repetir la animación indefinidamente
            repeatMode = Animation.REVERSE // Revertir la animación al finalizar cada ciclo
        }


        viewModel.stateRemove.observe(viewLifecycleOwner){
            when(it){
                State.LOADING->{
                    progressBarLouding.visibility = View.VISIBLE
                    constLayaut.visibility = View.INVISIBLE
                }
                State.SUCCESS->{
                    findNavController().navigateUp()            //accion de cambiar de pantalla
                }
                State.FAILURE->{
                    Snackbar.make(vista, "Error al liberar pokemon", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.statePokemon.observe(viewLifecycleOwner){
            when(it){
                State.LOADING->{

                }
                State.SUCCESS ->{
                    progressBarLouding.visibility = View.INVISIBLE
                    constLayaut.visibility = View.VISIBLE
                }
                State.FAILURE->{
                    Snackbar.make(vista, "Error al cargar datos del pokemon", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.pokemonData.observe(viewLifecycleOwner){
            setInfoPokemon(it)
        }

        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                State.LOADING->{
                    progressBarLouding.visibility = View.VISIBLE
                    constLayaut.visibility = View.INVISIBLE
                }
                State.SUCCESS->{

                }
                State.FAILURE->{
                    Snackbar.make(vista, "Error al liberar pokemon", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.pokemonPcData.observe(viewLifecycleOwner){

            labelLvl.text = it.nivel.toString()
            labelMote.text = it.mote!!.uppercase(Locale.getDefault())
            if(it.genero == true){
                labelGenero.text = "♂"
            }else if (it.genero == false){
                labelGenero.text = "♀"
            }else{
                labelGenero.text = "-"
            }
            labelHabilidad.text = it.habilidad
            if(it.objeto != null){
                labelObjeto.text = it.objeto
            } else {
                labelObjeto.text = "No Tiene"
            }
            labelNotas.text = it.descripcion

            viewModel.getPokemonById(it.idPokemon!!)
        }

        viewModel.stateLvl.observe(viewLifecycleOwner){
            when(it){
                State.LOADING->{

                }
                State.SUCCESS->{
                    val pokemonPc = viewModel.getPcPokemon()
                    labelLvl.text = pokemonPc.nivel.toString()
                }
                State.FAILURE->{
                    Snackbar.make(vista, "Error", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.pokemonEvolucionA.observe(viewLifecycleOwner){
            imgPokemon.startAnimation(animation)
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("El pokemon esta intentando evolucionar")
            alertDialog.setPositiveButton("ACEPTAR") { _, _ ->
                val pcPokemon = viewModel.getPcPokemon()
                viewModel.evolucionPokemon(pcPokemon,it)
            }
            alertDialog.setNegativeButton("CANCELAR") { dialog, _ ->
                imgPokemon.clearAnimation()
                dialog.cancel()
            }
            alertDialog.show()
        }

        viewModel.stateEvolucion.observe(viewLifecycleOwner){
            when(it){
                State.LOADING->{
                    //Animacion de Evolucion
                    //Snackbar.make(vista, "Procesando", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS->{
                    val pokemonAux = labelMote.text
                    val pokemon = viewModel.getPokemon()
                    setInfoPokemon(pokemon)
                    val pokemonPc = viewModel.getPcPokemon()
                    labelLvl.text = pokemonPc.nivel.toString()
                    labelMote.text = pokemonPc.mote!!.uppercase(Locale.getDefault())
                    if(pokemonPc.objeto != null){
                        labelObjeto.text = pokemonPc.objeto
                    } else {
                        labelObjeto.text = "No Tiene"
                    }

                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setTitle("Felizidade ${pokemonAux} \na evolucionado en ${pokemon.nombre!!.uppercase(Locale.getDefault())}")
                    alertDialog.setPositiveButton("OK") { _, _ ->
                    }
                    alertDialog.show()
                    //Finalizar Animacion de Evolucion
                    imgPokemon.clearAnimation()
                }
                State.FAILURE->{
                    imgPokemon.clearAnimation()
                    Snackbar.make(vista, "Error", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.stateItems.observe(viewLifecycleOwner){
            when(it){
                State.LOADING->{
                    Snackbar.make(vista, "Procesando", Snackbar.LENGTH_SHORT).show()
                }
                State.SUCCESS->{
                    val pokemonPc = viewModel.getPcPokemon()
                    if(pokemonPc.objeto != null) {
                        if (pokemonPc.idObjeto == 45) {
                            //Mas PS aumnta la Salud en +10

                        }else if(pokemonPc.idObjeto == 46) {
                            //Proteina aumnta Ataque en +10

                        }else if(pokemonPc.idObjeto == 47) {
                            //Hierro aumnta la defensa en +10

                        }else if(pokemonPc.idObjeto == 48) {
                            //Carrburante aumnta la Velocidad en +10

                        }else if(pokemonPc.idObjeto == 49) {
                            //Calcio aumnta la At. Esp en +10

                        }else if(pokemonPc.idObjeto == 50) {
                            //Si el item es un caramelo raro aumentar el nivel
                            viewModel.upLevelPokemon(pokemonPc.id!!)
                        }else if(pokemonPc.idObjeto == 51) {
                            //Mas PP aumnta un 20% de los PPs de un ataque

                        }else if(pokemonPc.idObjeto == 52) {
                            //Zinc aumnta la De. Esp en +10

                        }else if(pokemonPc.idObjeto == 53) {
                            //PP Maximos aumnta un 60% de los PPs de un ataque

                        }else{
                            labelObjeto.text = pokemonPc.objeto
                        }
                    }
                }
                State.FAILURE->{
                    Snackbar.make(vista, "Error", Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.items.observe(viewLifecycleOwner){

        }

        viewModel.itemsList.observe(viewLifecycleOwner){
            val multiAutoCompleteTextView = MultiAutoCompleteTextView(context)
            multiAutoCompleteTextView.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it.itemList))
            multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer()) // Opcional: para separar las opciones por comas
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Seleccionar Objeto")
            alertDialog.setView(multiAutoCompleteTextView)
            alertDialog.setPositiveButton("ACEPTAR") { _, _ ->
                val item = multiAutoCompleteTextView.text.toString()
                val pokemonPc = viewModel.getPcPokemon()
                viewModel.addItem(pokemonPc.id!!, item)
            }
            alertDialog.setNegativeButton("CANCELAR") { dialog, _ ->
                dialog.cancel()
            }
            alertDialog.show()
        }


        //Set editText Mote
        switchDatos.isChecked = false
        clDatos.visibility = View.VISIBLE
        clStats.visibility = View.INVISIBLE
        switchDatos.setOnCheckedChangeListener { _, isChecked ->
            // Realiza acciones según el estado del Switch
            if (isChecked) {
                // El Switch está activado
                clDatos.visibility = View.INVISIBLE
                clStats.visibility = View.VISIBLE

            } else {
                // El Switch está desactivado
                clDatos.visibility = View.VISIBLE
                clStats.visibility = View.INVISIBLE
            }
        }

        return vista
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        progressBarLouding.visibility = View.VISIBLE
        constLayaut.visibility = View.INVISIBLE

        val idPcPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).id
        viewModel.getPcPokemonByIdPokemon(idPcPokemon)

        btnLiberar.setOnClickListener{
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Esta seguro que quiere liberar a ${labelMote.text}?")
            alertDialog.setPositiveButton("SI") { _, _ ->
                viewModel.remuvePokemonPc(idPcPokemon)
            }
            alertDialog.setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
            alertDialog.show()
        }

        btnAddObjeto.setOnClickListener {

            //viewModel.getObjetos()

            val input = EditText(requireContext())
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Seleccionar Objeto")
            alertDialog.setView(input)
            alertDialog.setPositiveButton("ACEPTAR") { _, _ ->
                val item = input.text.toString()
                viewModel.addObjeto(idPcPokemon, item)
            }
            alertDialog.setNegativeButton("CANCELAR") { dialog, _ ->
                dialog.cancel()
            }
            alertDialog.show()

        }
        btnLevlUp.setOnClickListener {
            viewModel.upLevelPokemon(idPcPokemon)

        }

    }

    private fun setStats(stat:String, datoStat:Int){

        when(stat){
            "hp" ->{
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
            "Ataque Especial" ->{
                labelAtEsp.text = datoStat.toString()
                barrAtEsp.progress = datoStat
            }
            "Defensa Especial" ->{
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
    private fun setInfoPokemon(pokemon: Pokemon){
        labelId.text = pokemon.id.toString()
        labelName.text = pokemon.nombre!!.uppercase(Locale.getDefault())
        var cont = 0
        for(tipo in pokemon.tipo!!){
            tipo.idTipo?.let { it1 -> setImgTipo(it1, cont) }
            cont += 1
        }

        for(state in pokemon.stats!!){
            state.detalle?.nombre?.let { it1 ->
                state.statsBase?.let { it2 ->
                    setStats(it1,it2)
                }
            }
        }

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
    }


}
