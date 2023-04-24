package com.example.apppokedex.entities

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.adapters.PokemonAdapter


open class ActionLista(private val adapter: PokemonAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // No implementamos el movimiento
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Eliminar el objeto al desplazarlo
        adapter.deleteItem(viewHolder.adapterPosition)
    }
}
