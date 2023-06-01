package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()

    fun getUser(userName:String, password:String){
        state.postValue(State.LOADING)
        val dbFb = Firebase.firestore

        dbFb.collection("Usuarios")
            .whereEqualTo("userName", userName)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    state.postValue(State.SUCCESS)
                    val user = documents.toObjects<Usuario>()
                    //Guardar en SP
                    preferencesManager.saveUser(user[0])
                } else {
                    state.postValue(State.FAILURE)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                state.postValue(State.FAILURE)
            }
    }
}
