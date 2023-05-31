package com.example.apppokedex.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentAddPokemonViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()
    val stateUsuario : MutableLiveData<State> = MutableLiveData()


    fun getUser(): Usuario {
        return preferencesManager.getUserLogin()
    }

    fun updateUserData(user : Usuario){

        stateUsuario.postValue(State.LOADING)

        val dbFb = Firebase.firestore
        val usersCollection = dbFb.collection("Usuarios")

        val id = preferencesManager.getIdUser()

        user.id = id
        usersCollection.document(id)
            .set(user)
            .addOnSuccessListener {
                preferencesManager.saveUser(user)
                stateUsuario.postValue(State.SUCCESS)
            }
            .addOnFailureListener { exception ->
                stateUsuario.postValue(State.FAILURE)
            }
    }

    fun getPokemon():Pokemon{
        return preferencesManager.getPokemon()
    }

}