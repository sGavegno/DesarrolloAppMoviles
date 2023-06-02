package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.firebase.auth.AuthResult
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
class FragmentRegisterViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()
    val dbFb = Firebase.firestore

    fun regUserAuth(userNew:Usuario, email: String, password: String, confirmPassword:String): FirebaseUser?{
        state.postValue(State.LOADING)

        if ((userNew.userName?.length ?: 0) < 6) {
            state.postValue(State.PASSLENGTH)
            return null
        }

        if (password.length < 6 || confirmPassword.length < 6) {
            state.postValue(State.PASSLENGTH)
            return null
        }

        if(password == confirmPassword){
            var user: FirebaseUser?
            var newUser: FirebaseUser? = null

            //Check if user exists
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    user = getUserAuth(email, password)
                    if (user == null) {
                        newUser = createUserAuth(email, password)
                        if (newUser != null) { //Save user into the Shared Preference
                            preferencesManager.saveUser(userNew)
                            state.postValue(State.SUCCESS)
                        }
                    } else {
                        state.postValue(State.USEREXISTS)
                    }
                }
            } catch (e: Exception) {
                state.postValue(State.FAILURE)
                Log.d("getAuthFrom", "Raised Exception")
            }
            return newUser
        } else {
            state.postValue(State.PASNOTEQUAL)
            return null
        }
    }

    private suspend fun getUserAuth(email: String, password: String): FirebaseUser? {

        return try {
            var user: FirebaseUser? = null
            val auth: FirebaseAuth = Firebase.auth
            user = (auth.signInWithEmailAndPassword(email, password).await()).user
            user
        } catch (e: Exception) {
            Log.d("getUserAuth", "Raised Exception")
            null
        }
    }

    private suspend fun createUserAuth(email: String, password: String): FirebaseUser? {

        return try {
            lateinit var result: AuthResult
            val auth: FirebaseAuth = Firebase.auth
            result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            Log.d("createUserAuth", "Raised Exception")
            null
        }
    }

    fun addUser(userNew: Usuario){
        state.postValue(State.LOADING)
        //Buscar si exciste en la base de datos
        dbFb.collection("Usuarios")
            .whereEqualTo("userName", userNew.userName)
            .get()
            .addOnSuccessListener { documents ->
                if(documents.isEmpty){
                    // Add a new document with a generated ID
                    dbFb.collection("user")
                        .add(userNew)
                        .addOnSuccessListener { documentReference ->

                            Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                            //agrego el Id en la BD
                            val userId = documentReference.id
                            userNew.id = userId
                            dbFb.collection("user").document(userId)
                                .update("id",userId)
                                .addOnSuccessListener { Log.d("Firebase", "DocumentSnapshot successfully updated!")

                                    state.postValue(State.SUCCESS)
                                    //Guardar en SP
                                    preferencesManager.saveUser(userNew)
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firebase", "Error updating document", e)
                                    state.postValue(State.SUCCESS)
                                }
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error adding document", e)
                            state.postValue(State.FAILURE)
                        }
                }
                else
                {
                    state.postValue(State.FAILURE)

                    for (document in documents) {
                        Log.d("Firebase", "${document.id} => ${document.data}")
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
                state.postValue(State.FAILURE)
            }
    }

}