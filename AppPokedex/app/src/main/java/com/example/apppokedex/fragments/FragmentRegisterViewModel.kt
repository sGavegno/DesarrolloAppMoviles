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
class FragmentRegisterViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()

    val dbFb = Firebase.firestore
    fun addUser(userNew: Usuarios){

        state.postValue(State.LOADING)

        //Buscar si exciste en la base de datos
        dbFb.collection("user")
            .whereEqualTo("userName", userNew.userName)
            .get()
            .addOnSuccessListener { documents ->
                if(documents.isEmpty){
                    // Create a new user with a first and last name
                    val user = hashMapOf(
                        "userName" to userNew.userName,
                        "password" to userNew.password,
                        "name" to userNew.name,
                        "lastName" to userNew.lastName,
                        "email" to userNew.email,
                        "telefono" to userNew.telefono,
                        "direccion" to userNew.direccion,
                        "permisos" to false
                    )
                    // Add a new document with a generated ID
                    dbFb.collection("user")
                        .add(user)
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