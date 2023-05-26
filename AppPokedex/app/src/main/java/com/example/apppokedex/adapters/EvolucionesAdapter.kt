package com.example.apppokedex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.entities.EvolucionesCadena

class EvolucionesAdapter(
    private var evolucionList: List<EvolucionesCadena>?,
    var onClick: (Int) -> Unit                                     //Funcion como parametro
    ) : RecyclerView.Adapter<EvolucionesAdapter.EvolucionHolder>() {

    class EvolucionHolder(v: View) : RecyclerView.ViewHolder(v){

        private var db: AppDatabase? = null
        private var pokemonDao: PokemonDao? = null

        private var vista : View
        init{
            this.vista = v
        }

        fun setEvolucion(pokemon : EvolucionesCadena){

            val txtEvolucion : TextView = vista.findViewById(R.id.txtEvolucion)
            val imgEvolucion : ImageView = vista.findViewById(R.id.imgEvolucion)

            txtEvolucion.text = pokemon.Nombre

            val id = pokemon.Id
            if(id != null){
                if(id < 10){
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00$id.png").into(imgEvolucion)
                    imgEvolucion.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/00$id.png"
                }else if(id < 100){
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0$id.png").into(imgEvolucion)
                    imgEvolucion.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/0$id.png"
                }else{
                    Glide.with(vista).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$id.png").into(imgEvolucion)
                    imgEvolucion.tag = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$id.png"
                }
            }

        }
        fun getCard(): CardView {
            return vista.findViewById(R.id.cardEvolucion)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolucionHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_evoluciones,parent, false)
        return (EvolucionHolder(vista))
    }

    override fun onBindViewHolder(holder: EvolucionHolder, position: Int) {
        evolucionList?.get(position)?.let { holder.setEvolucion(it) }
        holder.getCard().setOnClickListener{
            onClick(position)               //
        }
    }

    override fun getItemCount(): Int {
        return evolucionList?.size ?: 0
    }

}