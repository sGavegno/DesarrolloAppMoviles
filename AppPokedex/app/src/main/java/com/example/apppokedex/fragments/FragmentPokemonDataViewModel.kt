package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.Pc
import com.example.apppokedex.entities.Pokemon
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPokemonDataViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val statePokemon : MutableLiveData<State> = MutableLiveData()
    val stateUsuario : MutableLiveData<State> = MutableLiveData()

    val pokemonData : MutableLiveData<Pokemon> = MutableLiveData()

    val dbFb = Firebase.firestore

    fun getPokemonById(idPokemon: Int){
        statePokemon.postValue(State.LOADING)

        dbFb.collection("Pokedex")
            .whereEqualTo("id", idPokemon)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    statePokemon.postValue(State.SUCCESS)
                    val pokemon = documents.toObjects<Pokemon>()
                    pokemonData.postValue(pokemon[0])
                } else {
                    statePokemon.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                statePokemon.postValue(State.FAILURE)
            }
    }

    fun remuvePokemonPc(id: Int){
        stateUsuario.postValue(State.LOADING)

        val dbFb = Firebase.firestore
        val usersCollection = dbFb.collection("Usuarios")
        val idUser = preferencesManager.getIdUser()

        val docRef = usersCollection.document(idUser)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val usuario = documentSnapshot.toObject(Usuario::class.java)

                // Obtén la MutableList `pc` del objeto Usuario
                val pcList = usuario?.pc

                // Elimina el elemento deseado de la lista
                pcList?.removeIf { pc -> pc.id == id }

                // Guarda los cambios en el documento
                usuario?.pc = pcList
                if (usuario != null) {
                    docRef.set(usuario)
                        .addOnSuccessListener {
                            // El elemento ha sido eliminado exitosamente
                            preferencesManager.saveUser(usuario)
                            stateUsuario.postValue(State.SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            // Ocurrió un error al eliminar el elemento
                            stateUsuario.postValue(State.FAILURE)
                        }
                }
            } else {
                // El documento no existe
                stateUsuario.postValue(State.FAILURE)
            }
        }.addOnFailureListener { exception ->
            // Ocurrió un error al obtener el documento
            stateUsuario.postValue(State.FAILURE)
        }

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

    fun getIdUser():String{
        return preferencesManager.getIdUser()
    }

    fun getUserPokemonById(idPokemon: Int) : Pc? {
        return preferencesManager.getUserPokemon(idPokemon)
    }

    fun getUser(): Usuario {
        return preferencesManager.getUserLogin()
    }

}