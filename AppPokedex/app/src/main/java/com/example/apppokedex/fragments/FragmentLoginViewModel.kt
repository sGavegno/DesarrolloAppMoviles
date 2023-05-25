package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuarios
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()

    fun getUser(userName:String, password:String){
        state.postValue(State.LOADING)
        val dbFb = Firebase.firestore
        var user = Usuarios( "", "", "", "", "", "", "", "", false)

        dbFb.collection("user")
            .whereEqualTo("userName", userName)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    state.postValue(State.SUCCESS)

                    val usuarioAux = documents.documents[0]
                    user.id = usuarioAux.id
                    user.userName = usuarioAux.getString("userName").toString()
                    user.password = usuarioAux.getString("password").toString()
                    user.name = usuarioAux.getString("name").toString()
                    user.lastName = usuarioAux.getString("lastName").toString()
                    user.email = usuarioAux.getString("email").toString()
                    user.telefono = usuarioAux.getString("telefono").toString()
                    user.direccion = usuarioAux.getString("direccion").toString()
                    user.permisos = usuarioAux.getBoolean("permisos") == true
                    //Guardar en SP
                    preferencesManager.saveUser(user)
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
