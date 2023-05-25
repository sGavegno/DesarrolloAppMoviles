package com.example.apppokedex.fragments

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
class FragmentUserViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()

    fun getUserData():Usuarios{
        return preferencesManager.getUserLogin()
    }

    fun getUserId(): String{
        return preferencesManager.getIdUser()
    }
    fun getUserPermisos(): Boolean{
        return preferencesManager.getPermisosUser()
    }

    fun getUserName(): String{
        return preferencesManager.getNameUser()
    }

    fun getUserPassword(): String{
        return preferencesManager.getPasswordUser()
    }

    fun updateUserData(user : Usuarios){

        state.postValue(State.LOADING)

        val dbFb = Firebase.firestore
        val usersCollection = dbFb.collection("user")

        val id = preferencesManager.getIdUser()

        user.id = id
        usersCollection.document(id)
            .set(user)
            .addOnSuccessListener {
                preferencesManager.saveUser(user)
                state.postValue(State.SUCCESS)
            }
            .addOnFailureListener { exception ->
                state.postValue(State.FAILURE)
            }
    }
}