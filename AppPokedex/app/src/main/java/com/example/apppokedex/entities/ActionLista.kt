package com.example.apppokedex.entities

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.adapters.PokemonAdapter
import com.example.apppokedex.database.PokemonDao

open class ActionLista(private val adapter: PokemonAdapter, private val dao: PokemonDao?, private val pokeList: MutableList<Pokemons?>?) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

        return false
    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {

    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Eliminar el objeto al desplazarlo
        val idPoke = pokeList?.get(viewHolder.adapterPosition)?.idPokemon
        val pokemon = idPoke?.let { dao?.fetchPokemonByIdPokemon(it) }
        pokemon?.let { dao?.delete(it) }
        adapter.deleteItem(viewHolder.adapterPosition)
    }
}
