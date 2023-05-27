package com.example.apppokedex.entities

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.adapters.PokemonAdapter

open class ActionLista(private val adapter: PokemonAdapter, private val pokeList: MutableList<Pokedex>) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

        return false
    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {

    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Eliminar el objeto al desplazarlo
        val idPoke = pokeList[viewHolder.adapterPosition].id

        adapter.deleteItem(viewHolder.adapterPosition)
    }
}
