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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.EvolucionesAdapter
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.PokemonUserDao
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPokedexData : Fragment() {

    val viewModel: FragmentPokedexDataViewModel by viewModels()

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

        return vista
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        val idUser = viewModel.getIdUser()

        val idPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).idPokemon

        viewModel.getPokemonById(idPokemon)

        viewModel.pokemonData.observe(this){

            labelName.text = it.Nombre
            labelId.text = it.Id.toString()
            var cont = 0
            for(tipo in it.Tipo!!){
                tipo.Id_Tipo?.let { it1 -> setImgTipo(it1, cont) }
                cont += 1
            }

            labelGeneracion.text = it.Detalle?.IdGeneracion.toString()
            labelDebilidad.text = "Implementar Tabla PokemonTipos"
            labelDescripcion.text = "pokemon.descripcion"
            var altura = it.Altura
            val alturaString = (altura?.div(10)).toString() + "," + (altura?.rem(10)).toString() + " m"
            labelAltura.text = alturaString
            var peso = it.Peso
            val pesoString = (peso?.div(10)).toString() + "," + (peso?.rem(10)).toString() + " kg"
            labelPeso.text = pesoString

            var habilidadString = ""
            for(habilidad in it.Habilidades!!){
                habilidadString += (habilidad.Detalle?.Nombre ?: "Habilidad")
                habilidadString += "\n"
            }
            labelHabilidad.text = habilidadString

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
            it.Detalle?.Id_CadenaEvolutiva?.let { it1 -> viewModel.getEvolucionesById(it1) }
        }

        viewModel.pokemonEvolucionData.observe(this){
            val evolcuinesLista = it.CadenaEvolutiva?.sortedBy { it.Id }
            if(evolcuinesLista != null){

                adapter = EvolucionesAdapter(evolcuinesLista){ position ->
                    //Guardar datos actualizados
                    val idPoke = evolcuinesLista[position].Id
                    val action = FragmentPokedexDataDirections.actionFragmentPokedexDataSelf(
                        idPoke!!
                    )
                    findNavController().navigate(action)            //accion de cambiar de pantalla
                }
                val layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                recEvoluciones.layoutManager = layoutManager

                //recEvoluciones.layoutManager = GridLayoutManager(context, PokemonEvolucionList.size)             //da formato a la lista
                recEvoluciones.adapter = adapter
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
