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
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.PokemonTipo

class PokemonAdapter(
    private var pokemonList: MutableList<Pokemon>,
    private val listener: PokemonAdapterListener          //Funcion como parametro
) : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>() {

    interface PokemonAdapterListener {
        fun onCardViewClick(pokemon: Pokemon,position: Int)
        fun onButtonClick(pokemon: Pokemon)
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
        fun setTipo(tipo: List<PokemonTipo>?) {
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
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        val item = pokemonList[position]
        item.let { it.Id?.let { it1 -> holder.setId(it1) } }
        item.let { it.Nombre?.let { it1 -> holder.setName(it1) } }
        //item?.let { holder.setTipo(it.Tipo) }
        val id = item.Id ?: 0
        if(id < 10){
            pokemonList[position].let { holder.setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00"+ it.Id +".png") }
        }else if(id < 100){
            pokemonList[position].let { holder.setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0"+ it.Id +".png") }
        }else{
            pokemonList[position].let { holder.setImagen("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"+ it.Id +".png") }
        }

        holder.getCard().setOnClickListener {
            pokemonList[position].let { it1 -> listener.onCardViewClick(it1,position) }
        }
        holder.btnAddPokemon.setOnClickListener {
            item.let { it1 -> listener.onButtonClick(it1) }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int) {
        // Eliminar el objeto en la posición especificada
        pokemonList.removeAt(position)
        notifyDataSetChanged()              //actializa el adapter
    }
}