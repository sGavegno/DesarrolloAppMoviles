package com.example.apppokedex.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentRegisterViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()

    val dbFb = Firebase.firestore
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