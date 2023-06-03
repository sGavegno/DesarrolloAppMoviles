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
                    result!!.email?.let { getUserFireBase(it) }
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
            result
        } catch (e: Exception) {
            Log.d("getAuthFrom", "Raised Exception")
            null
        }
    }

    private suspend fun getUserFireBase(email: String):Usuario?{
        val dbFb = Firebase.firestore

        return try {
            val documentReference = dbFb.collection("Usuarios").whereEqualTo("email", email).get().await()
            if(!documentReference.isEmpty){
                val user = documentReference.toObjects<Usuario>()
                //Guardar en SP
                preferencesManager.saveUser(user[0])
                return user[0]
            }
            null
        } catch (e: Exception) {
            Log.d("Firebase", "Error getting documents: ")
            null
        }
    }

}
