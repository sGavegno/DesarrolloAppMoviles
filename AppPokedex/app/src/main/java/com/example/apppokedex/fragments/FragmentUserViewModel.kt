package com.example.apppokedex.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class FragmentUserViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()
    val stateImageUpload = SingleLiveEvent<State>()
    val stateImageDownloadUri = SingleLiveEvent<State>()
    val stateImageDelete = SingleLiveEvent<State>()

    val imageStorage = SingleLiveEvent<String?>()


    fun getUserData(): Usuario {
        return preferencesManager.getUserLogin()
    }

    fun uploadStorageImage(bitmap: Bitmap){
        stateImageUpload.postValue(State.LOADING)

        val storage = Firebase.storage
        val storageRef = storage.reference
        val user =preferencesManager.getIdUser()
        val fileName = "img.jpg"
        val imagenRef = storageRef.child("$user/userImg/$fileName")
        try {
            viewModelScope.launch(Dispatchers.IO) {
                // Convierte un Bitmap en un array de bytes
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageView = stream.toByteArray()

                imagenRef.putBytes(imageView)
                    .addOnFailureListener {
                        // Handle unsuccessful uploads
                        stateImageUpload.postValue(State.FAILURE)
                    }
                    .addOnSuccessListener { taskSnapshot ->
                        // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                        Log.d("Storage", "taskSnapshot $taskSnapshot")
                    }
                    .addOnProgressListener {
                        val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                        Log.d("Storage", "Upload is $progress% done")
                    }
                    .addOnPausedListener {
                        Log.d("Storage", "Upload is paused")
                    }
                    .continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        imagenRef.downloadUrl
                    }
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            stateImageUpload.postValue(State.FAILURE)
                            Log.d("Storage", "downloadUri is $downloadUri")
                        } else {
                            // Handle failures
                            stateImageUpload.postValue(State.LOADING)
                        }
                    }
            }
        } catch (e: Exception) {
            state.postValue(State.FAILURE)
            Log.d("downloadUriStorage", "Raised Exception")
            stateImageUpload.postValue(State.FAILURE)
        }
    }

    fun downloadUriStorage(){
        stateImageDownloadUri.postValue(State.LOADING)
        val storage = Firebase.storage
        val storageRef = storage.reference

        val user =preferencesManager.getIdUser()
        val fileName = "img.jpg"
        val imagenRef = storageRef.child("$user/userImg/$fileName")
        try {
            viewModelScope.launch(Dispatchers.IO) {
                imagenRef.downloadUrl.addOnSuccessListener { uri ->
                    // La URL de descarga se obtuvo exitosamente
                    val imageUrl = uri.toString()
                    imageStorage.postValue(imageUrl)
                    stateImageDownloadUri.postValue(State.SUCCESS)
                }.addOnFailureListener { exception ->
                    // Ocurrió un error al obtener la URL de descarga
                    // Maneja el error según tus necesidades
                    Log.d("StorageError", "Error: $exception")
                    imageStorage.postValue(null)
                    stateImageDownloadUri.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            state.postValue(State.FAILURE)
            Log.d("downloadUriStorage", "Raised Exception")
            imageStorage.postValue(null)
            stateImageDownloadUri.postValue(State.FAILURE)
        }
    }

    fun downloadStorageImage(){
        val storage = Firebase.storage
        val storageRef = storage.reference

        val pathName = "UserImage"
        val fileName = "userRed.jpg"
        val imagesRef = storageRef.child("$pathName/$fileName")

        val oneMegaByte: Long = 1024 * 1024
        imagesRef.getBytes(oneMegaByte)
            .addOnSuccessListener { bytes ->
                // Los bytes de la imagen se descargaron exitosamente
                // Convierte los bytes en un Bitmap
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
            .addOnFailureListener {
            // Handle any errors
            }

    }

    fun deleteStorageImage(){
        val storage = Firebase.storage
        val storageRef = storage.reference

        val pathName = "UserImage"
        val fileName = "userRed.jpg"
        val imagesRef = storageRef.child("$pathName/$fileName")
        stateImageDelete.postValue(State.LOADING)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                // Delete the file
                imagesRef.delete().addOnSuccessListener {
                    // File deleted successfully
                    stateImageDelete.postValue(State.SUCCESS)
                }.addOnFailureListener {
                    // Uh-oh, an error occurred!
                    stateImageDelete.postValue(State.FAILURE)
                }
            }
        } catch (e: Exception) {
            state.postValue(State.FAILURE)
            Log.d("downloadUriStorage", "Raised Exception")
            stateImageDelete.postValue(State.FAILURE)
        }
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