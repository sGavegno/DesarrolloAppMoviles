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
import com.example.apppokedex.entities.Pokemons

class PokemonUserAdapter(
    var pokemonList: MutableList<Pokemons?>?,
    private val listener: PokemonUserAdapterListener
) : RecyclerView.Adapter<PokemonUserAdapter.PokemonUserHolder>()  {

    interface PokemonUserAdapterListener {
        fun onCardViewClick(pokemon: Pokemons, position: Int)
    }

    class PokemonUserHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view : View
        init {
            this.view = v
        }
        fun setId(id : Int){
            val txtId : TextView = view.findViewById(R.id.txtIdPokeUserDato)
            txtId.text = id.toString()
        }
        fun setName(name : String){
            val txtName : TextView = view.findViewById(R.id.txtNombrePokeUser)
            txtName.text = name
        }
        fun setTipo(tipo : String) {
        //    val txtTipo: TextView = view.findViewById(R.id.txtTipoPokeUser)
        //    txtTipo.text = tipo
        }
        fun setImagen(imagen : String) {
            val imgPoke = view.findViewById<ImageView>(R.id.imgPokeUser)
            Glide.with(view).load(imagen).into(imgPoke)
        }
        fun getCard(): CardView {
            return view.findViewById(R.id.cardPokemonUser)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonUserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_user,parent, false)
        return (PokemonUserHolder(view))
    }

    override fun getItemCount(): Int {
        return pokemonList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PokemonUserHolder, position: Int) {
        pokemonList?.get(position)?.let { holder.setId(it.idPokemon) }
        pokemonList?.get(position)?.let { holder.setName(it.nombre) }
        pokemonList?.get(position)?.let { holder.setImagen(it.imgURL) }
        holder.getCard().setOnClickListener{
            pokemonList?.get(position)?.let { it1 -> listener.onCardViewClick(it1, position) }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int) {
        // Eliminar el objeto en la posición especificada
        pokemonList?.removeAt(position)
        notifyDataSetChanged()
    }
}