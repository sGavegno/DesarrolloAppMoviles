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

class EvolucionesAdapter(
    private var evolucionList: List <Int>,
    var onClick: (Int) -> Unit                                     //Funcion como parametro
    ) : RecyclerView.Adapter<EvolucionesAdapter.EvolucionHolder>() {

    class EvolucionHolder(v: View) : RecyclerView.ViewHolder(v){

        private var db: AppDatabase? = null
        private var pokemonDao: PokemonDao? = null

        private var vista : View
        init{
            this.vista = v
        }

        fun setEvolucion(id : Int){

            db = AppDatabase.getInstance(vista.context)
            pokemonDao = db?.pokemonDao()

            val txtEvolucion : TextView = vista.findViewById(R.id.txtEvolucion)
            val imgEvolucion : ImageView = vista.findViewById(R.id.imgEvolucion)
            val pokemonFind = pokemonDao?.fetchPokemonByIdPokemon(id)
            if(pokemonFind != null)
            {
                txtEvolucion.text = pokemonFind.nombre
                Glide.with(vista).load(pokemonFind.imgURL).into(imgEvolucion)
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
        holder.setEvolucion(evolucionList[position])
        holder.getCard().setOnClickListener{
            onClick(position)               //
        }
    }

    override fun getItemCount(): Int {
        return evolucionList.size
    }

}