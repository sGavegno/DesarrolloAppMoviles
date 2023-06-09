package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedex.PreferencesManager
import com.example.apppokedex.SingleLiveEvent
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
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
import java.io.IOException
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class FragmentLoginViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val state = SingleLiveEvent<State>()
    val stateLocattion = SingleLiveEvent<State>()

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

    private suspend fun getUserAuth(email: String, password: String): FirebaseUser? {

        return try {
            val result: FirebaseUser?
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

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(fusedLocationClient: FusedLocationProviderClient, geocoder: Geocoder) {
        stateLocattion.postValue(State.LOADING)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Verifica si la ubicación se pudo obtener correctamente
                        if (location != null) {
                            val ubicacion = obtenerPaisCiudadDesdeCoordenadas(geocoder,location.latitude,location.longitude)
                            preferencesManager.saveUbicacion(ubicacion)
                            preferencesManager.saveLocation(location)
                            stateLocattion.postValue(State.SUCCESS)
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Maneja el error al obtener la ubicación
                        Log.e("Geolocat", "Error al obtener la ubicación: ${exception.message}")
                        stateLocattion.postValue(State.FAILURE)
                    }
            }
        }catch (e: Exception){
            stateLocattion.postValue(State.FAILURE)
        }
    }

    private fun obtenerPaisCiudadDesdeCoordenadas(geocoder: Geocoder, latitud: Double, longitud: Double): String {
        var paisCiudad = "Desconocido"

        try {
            val direcciones: List<Address> = geocoder.getFromLocation(latitud, longitud, 1) as List<Address>

            if (direcciones.isNotEmpty()) {
                val direccion: Address = direcciones[0]
                val pais = direccion.countryName
                val ciudad = direccion.locality

                if (ciudad != null && pais != null) {
                    paisCiudad = "$ciudad, $pais"
                } else if (pais != null) {
                    paisCiudad = pais
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return paisCiudad
    }

}
