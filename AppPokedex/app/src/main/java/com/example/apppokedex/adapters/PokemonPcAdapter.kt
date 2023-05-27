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
import com.example.apppokedex.fragments.FragmentPc
import java.util.Locale

class PokemonPcAdapter(
    var pokemonList: MutableList<Pokedex>,
    private val listener: FragmentPc
) : RecyclerView.Adapter<PokemonPcAdapter.PokemonPcHolder>()  {

    interface PokemonPcAdapterListener {
        fun onCardViewClick(pokemon: Pokedex, position: Int)
        fun onButtonClick(pokemon: Pokedex)
    }

    class PokemonPcHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view : View
        init {
            this.view = v
        }
        fun setAdapter(pokedex: Pokedex){
            //setId
            val txtId : TextView = view.findViewById(R.id.txtIdPokePcDato)
            txtId.text = pokedex.id.toString()
            //setName
            val txtName: TextView = view.findViewById(R.id.txtNombrePokePcDato)
            val nombre = pokedex.name!!.uppercase(Locale.getDefault())
            txtName.text = nombre
            //setImg
            val id = pokedex.id
            if(id!! < 10){
                setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00${pokedex.id}.png")
            }else if(id < 100){
                setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0${pokedex.id}.png")
            }else{
                setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokedex.id}.png")
            }
            //setTipo
            val img = mutableListOf(0,0)
            var cont = 0
            for (idTipo in pokedex.tipo!!){
                img[cont] = idTipo.idTipo?.let { getImgTipo(it) }!!
                cont += 1
            }
            val imgTipo1 = view.findViewById<ImageView>(R.id.imgPcAdapterTipo1)
            val imgTipo2 = view.findViewById<ImageView>(R.id.imgPcAdapterTipo2)
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
        private fun setImagen(imagen : String) {
            val imgPoke = view.findViewById<ImageView>(R.id.imgPokePc)
            Glide.with(view).load(imagen).into(imgPoke)
        }
        fun getCard(): CardView {
            return view.findViewById(R.id.cardPokemonUser)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonPcHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_user,parent, false)
        return (PokemonPcHolder(view))
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonPcHolder, position: Int) {
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
        notifyDataSetChanged()
    }
}