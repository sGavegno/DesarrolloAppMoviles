package com.example.apppokedex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.entities.Pokemons

class PokemonAdapter(
    private var pokemonList: MutableList<Pokemons?>?,
    private val listener: PokemonAdapterListener          //Funcion como parametro
) : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>() {

    interface PokemonAdapterListener {
        fun onCardViewClick(pokemon: Pokemons,position: Int)
        fun onButtonClick(pokemon: Pokemons)
    }

    class PokemonHolder(v: View) : RecyclerView.ViewHolder(v){

        val btnAddPokemon: Button = itemView.findViewById(R.id.btnAddPokemonUser)

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
 //           val txtTipo: TextView = view.findViewById(R.id.txtTipo)
 //           txtTipo.text = tipo
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
        return pokemonList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        val item = pokemonList?.get(position)
        pokemonList?.get(position)?.let { holder.setId(it.idPokemon) }
        pokemonList?.get(position)?.let { holder.setName(it.nombre) }
        //pokemonList?.get(position)?.let { holder.setTipo(it.tipo) }
        pokemonList?.get(position)?.let { holder.setImagen(it.imgURL) }

        holder.getCard().setOnClickListener {
            pokemonList?.get(position)?.let { it1 -> listener.onCardViewClick(it1,position) }
        }
        holder.btnAddPokemon.setOnClickListener {
            item?.let { it1 -> listener.onButtonClick(it1) }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int) {
        // Eliminar el objeto en la posición especificada
        pokemonList?.removeAt(position)
        notifyDataSetChanged()              //actializa el adapter
    }
}