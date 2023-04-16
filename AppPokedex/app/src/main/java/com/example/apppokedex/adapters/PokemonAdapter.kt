package com.example.apppokedex.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pokemons

class PokemonAdapter(
    var pokemonList: MutableList <Pokemons>,
    var onClick: (Int) -> Unit                                     //Funcion como parametro
) : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>() {

    class PokemonHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view :View
        init {
            this.view = v
        }

        fun setId(id : Int){
            val txtId : TextView = view.findViewById(R.id.txtId)
            txtId.text = id.toString()
        }
        fun setName(name : String){
            val txtName : TextView = view.findViewById(R.id.txtName)
            txtName.text = name
        }
        fun setTipo(tipo : String) {
            val txtTipo: TextView = view.findViewById(R.id.txtTipo)
            txtTipo.text = tipo
        }
        fun setImagen(imagen : String) {
            val imgPoke = view.findViewById<ImageView>(R.id.imgPoke)
            Glide.with(view).load(imagen).into(imgPoke)
        }

        fun getCard():CardView{
            return view.findViewById(R.id.cardPokemon)
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
        holder.setId(pokemonList[position].id)
        holder.setName(pokemonList[position].nombre)
        holder.setTipo(pokemonList[position].tipo)
        holder.setImagen(pokemonList[position].imgURL)
        holder.getCard().setOnClickListener{
            onClick(position)               //
        }
    }
}