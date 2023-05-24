package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.entities.Users
import com.example.apppokedex.entities.Usuarios
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewModel @Inject constructor(): ViewModel() {

    val usuario : MutableLiveData<Usuarios> = MutableLiveData()
    val state : MutableLiveData<State> = MutableLiveData()

    fun getUser(userName:String, password:String){
        state.postValue(State.LOADING)
        val dbFb = Firebase.firestore
        var users : Usuarios = Usuarios( "", "", "", "", "", "", "", "", false)

        dbFb.collection("user")
            .whereEqualTo("userName", userName)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    state.postValue(State.SUCCESS)
                    val usuarioAux = documents.documents[0]

                    users.id = usuarioAux.id
                    users.userName = usuarioAux.getString("userName").toString()
                    users.password = usuarioAux.getString("password").toString()
                    users.name = usuarioAux.getString("name").toString()
                    users.lastName = usuarioAux.getString("lastName").toString()
                    users.email = usuarioAux.getString("email").toString()
                    users.telefono = usuarioAux.getString("telefono").toString()
                    users.direccion = usuarioAux.getString("direccion").toString()
                    users.permisos = usuarioAux.getBoolean("permisos") == true

                    //Guardar en SP
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

enum class State {
    LOADING,
    SUCCESS,
    FAILURE
}