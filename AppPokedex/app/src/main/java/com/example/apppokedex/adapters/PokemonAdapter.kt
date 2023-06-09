package com.example.apppokedex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pokedex
import com.example.apppokedex.fragments.FragmentPokedex
import java.util.Locale

class PokemonAdapter(
    private var pokemonList: MutableList<Pokedex>,
    private val listener: FragmentPokedex          //Funcion como parametro
) : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>() {

    interface PokemonAdapterListener {
        fun onCardViewClick(pokemon: Pokedex, position: Int)

    }

    class PokemonHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view :View
        init {
            this.view = v
        }
        @SuppressLint("SetTextI18n")
        fun setAdapter(pokedex: Pokedex){
            //setId
            val txtId : TextView = view.findViewById(R.id.txtId)
            txtId.text = pokedex.id.toString()

            //setGeneracion
            val txtGeneracion : TextView = view.findViewById(R.id.txtGeneracion)
            txtGeneracion.text = "G. ${pokedex.generacion}"
            //setTName
            val txtName: TextView = view.findViewById(R.id.txtName)
            val nombre = pokedex.name!!.uppercase(Locale.getDefault())
            txtName.text = nombre
            //setImg
            if(nombre != "??????"){
                val id = pokedex.id
                if(id!! < 10){
                    setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${pokedex.id}.png")
                }else if(id < 100){
                    setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${pokedex.id}.png")
                }else{
                    setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokedex.id}.png")
                }
            } else {
                setImagen("https://img1.freepng.es/20171220/etw/question-mark-png-5a3a530fe92093.6258665915137717919549.jpg")
//                setImagen("https://img2.freepng.es/20171217/d5b/question-mark-png-5a3698322ab363.8566429715135273461749.jpg")
            }
            //setTipo
            val img = mutableListOf(0,0)
            var cont = 0
            for (tipo in pokedex.tipo!!){
                img[cont] = tipo.idTipo?.let { getImgTipo(it) }!!
                cont += 1
            }
            val imgTipo1 = view.findViewById<ImageView>(R.id.imgPokedexAdapterTipo1)
            val imgTipo2 = view.findViewById<ImageView>(R.id.imgPokedexAdapterTipo2)
            if(img[0] != 0){
                Glide.with(view).load(img[0]).into(imgTipo1)
                imgTipo1.visibility = View.VISIBLE
            } else {
                imgTipo1.visibility = View.INVISIBLE
            }
            if(img[1] != 0){
                Glide.with(view).load(img[1]).into(imgTipo2)
                imgTipo2.visibility = View.VISIBLE
            } else {
                imgTipo2.visibility = View.INVISIBLE
            }

        }
        fun setImagen(imagen : String) {
            val imgPoke = view.findViewById<ImageView>(R.id.imgPoke)
            Glide.with(view).load(imagen).into(imgPoke)
        }

        fun getCard():CardView{
            return view.findViewById(R.id.cardPokemon)
        }

        private fun getImgTipo(idTipo : Int): Int{
            var tipo = 0
            when(idTipo){
                1->{
                    tipo = R.drawable.tipo_normal
                }
                2->{
                    tipo = R.drawable.tipo_lucha
                }
                3->{
                    tipo = R.drawable.tipo_volador
                }
                4->{
                    tipo = R.drawable.tipo_veneno
                }
                5->{
                    tipo = R.drawable.tipo_tierra
                }
                6->{
                    tipo = R.drawable.tipo_roca
                }
                7->{
                    tipo = R.drawable.tipo_bicho
                }
                8->{
                    tipo = R.drawable.tipo_fantasma
                }
                9->{
                    tipo = R.drawable.tipo_acero
                }
                10->{
                    tipo = R.drawable.tipo_fuego
                }
                11->{
                    tipo = R.drawable.tipo_agua
                }
                12->{
                    tipo = R.drawable.tipo_planta
                }
                13->{
                    tipo = R.drawable.tipo_electrico
                }
                14->{
                    tipo = R.drawable.tipo_psiquico
                }
                15->{
                    tipo = R.drawable.tipo_hielo
                }
                16->{
                    tipo = R.drawable.tipo_dragon
                }
                17->{
                    tipo = R.drawable.tipo_siniestro
                }
                18->{
                    tipo = R.drawable.tipo_hada
                }
            }
            return tipo
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon,parent, false)
        return (PokemonHolder(view))
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        val item = pokemonList[position]
        holder.setAdapter(item)
        holder.getCard().setOnClickListener {
            pokemonList[position].let { it1 -> listener.onCardViewClick(it1,position) }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int) {
        // Eliminar el objeto en la posición especificada
        pokemonList.removeAt(position)
        notifyDataSetChanged()              //actializa el adapter
    }


}