package com.example.apppokedex.entities

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.adapters.PokemonUserAdapter
import com.example.apppokedex.database.PokemonUserDao
import com.google.android.material.internal.ContextUtils.getActivity

open class ActionListaPokemonUser(private val adapter: PokemonUserAdapter, private val dao: PokemonUserDao?, private val pokeList: MutableList<Pokemons?>?, private val idUser: Int?) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)  {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // No implementamos el movimiento
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Eliminar el objeto al desplazarlo
        val idPoke = pokeList?.get(viewHolder.adapterPosition)?.idPokemon
        val pokemonUser = idPoke?.let { idUser?.let { it1 -> dao?.fetchPokemonUserByPokemon(it1, it) } }
        pokemonUser?.let { dao?.delete(it) }
        adapter.deleteItem(viewHolder.adapterPosition)
    }
}