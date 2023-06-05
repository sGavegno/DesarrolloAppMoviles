package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FragmentUserViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()

    fun getUserData(): Usuario {
        return preferencesManager.getUserLogin()
    }

    fun updateUserData(email: String, password: String, usuario: Usuario): FirebaseUser?{
        state.postValue(State.LOADING)
        var user: FirebaseUser? = null
        //Check if user exists
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                user = getUserAuth(email, password)
                if (user == null) {
                    state.postValue(State.PASNOTEQUAL)
                } else {
                    val result = updateUserFireBase(usuario)
                    if (result != null) {
                        state.postValue(State.SUCCESS)
                    } else {
                        state.postValue(State.FAILURE)
                    }
                }
            }
            user
        } catch (e: Exception) {
            state.postValue(State.FAILURE)
            Log.d("getAuthFrom", "Raised Exception")
            null
        }
    }

    fun removePokemonUser( email: String, password: String, usuario: Usuario): FirebaseUser?{
        state.postValue(State.LOADING)
        var user: FirebaseUser? = null
        //Check if user exists
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                user = getUserAuth(email, password)
                if (user == null) {
                    state.postValue(State.PASNOTEQUAL)
                } else {
                    usuario.pokedex = mutableListOf()
                    usuario.pc = mutableListOf()
                    val result = updateUserFireBase(usuario)
                    if (result != null) {
                        state.postValue(State.SUCCESS)
                    } else {
                        state.postValue(State.FAILURE)
                    }
                }
            }
            user
        } catch (e: Exception) {
            state.postValue(State.FAILURE)
            Log.d("getAuthFrom", "Raised Exception")
            null
        }
    }

    private suspend fun getUserAuth(email: String, password: String): FirebaseUser? {

        return try {
            val user: FirebaseUser?
            val auth: FirebaseAuth = Firebase.auth
            user = (auth.signInWithEmailAndPassword(email, password).await()).user
            user
        } catch (e: Exception) {
            Log.d("getUserAuth", "Raised Exception")
            null
        }
    }

    private suspend fun updateUserFireBase(user: Usuario):Usuario?{
        val dbFb = Firebase.firestore
        val id = preferencesManager.getIdUser()
        user.id = id
        return try {
            dbFb.collection("Usuarios").document(id).set(user).await()
            preferencesManager.saveUser(user)
            user
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

}