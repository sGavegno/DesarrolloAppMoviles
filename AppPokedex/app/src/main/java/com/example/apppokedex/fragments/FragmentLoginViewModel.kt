package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()

    fun userLogin(email: String, password: String): FirebaseUser? {
        state.postValue(State.LOADING)
        return try {
            var result: FirebaseUser? = null
            viewModelScope.launch(Dispatchers.IO) {
                result = getUserAuth(email, password)
                Log.d("myFirebaseLogin - Resultado", "$result")
                if(result!=null) {
                    //Aplicar Corutina
                    result!!.email?.let { getUserFireBase(it) }
                    //////////////////
                    state.postValue(State.SUCCESS)
                }
                else{
                    state.postValue(State.FAILURE)
                }
            }
            result
        } catch (e: Exception) {
            Log.d("myFireBaseLogin", "A ver $e")
            state.postValue(State.FAILURE)
            null
        }
    }

    suspend fun getUserAuth(email: String, password: String): FirebaseUser? {

        return try {
            var result: FirebaseUser?= null
            val auth: FirebaseAuth = Firebase.auth
            result = (auth.signInWithEmailAndPassword(email, password).await()).user
            if (result != null) { //Save user into the Shared Preference

            }
            result
        } catch (e: Exception) {
            Log.d("getAuthFrom", "Raised Exception")
            null
        }
    }

    private fun getUserFireBase(email: String){
        val dbFb = Firebase.firestore
        dbFb.collection("Usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    val userLogin = documents.toObjects<Usuario>()
                    //Guardar en SP
                    preferencesManager.saveUser(userLogin[0])
                } else {
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
            }
    }

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
