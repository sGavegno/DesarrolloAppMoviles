package com.example.apppokedex.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentLogin : Fragment() {

    private lateinit var imgTitulo : ImageView
    private lateinit var inputTxtUser : EditText
    private lateinit var inputTxtPass : EditText
    private lateinit var btnNexScreen : Button
    private lateinit var btnSingin : Button

    lateinit var vista : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_login, container, false)
        imgTitulo = vista.findViewById(R.id.imgLogin)
        btnNexScreen = vista.findViewById(R.id.btnLogin)
        btnSingin = vista.findViewById(R.id.btnSingin)
        inputTxtUser = vista.findViewById(R.id.editTxtUserName)
        inputTxtPass = vista.findViewById(R.id.editTxtPass)
        return vista
    }

    override fun onStart() {
        super.onStart()

        Glide.with(vista).load(R.drawable.pokedex_logo).into(imgTitulo)

        val dbFb = Firebase.firestore

        btnNexScreen.setOnClickListener{
            //Analizo si los parametros estan en la base de datos
            val inputTxtUserName : String = inputTxtUser.text.toString()
            val inputTxtUserPass : String = inputTxtPass.text.toString()

            dbFb.collection("user")
                .whereEqualTo("userName", inputTxtUserName)
                .whereEqualTo("password", inputTxtUserPass)
                .get()
                .addOnSuccessListener { documents ->
                    if(!documents.isEmpty){
                        val sharedPref = context?.getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                        if (sharedPref != null) {
                            with (sharedPref.edit()) {
                                putString("UserID", documents.documents[0].id)
                                putInt("pos_recycler_view_pokedex", 0)
                                putInt("pos_recycler_view_pc", 0)
                                commit()
                            }
                        }
                        inputTxtUser.setText("")
                        inputTxtPass.setText("")
                        val intent = Intent(activity, activity_home::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(vista, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Firebase", "Error getting documents: ", exception)
                }
        }

        btnSingin.setOnClickListener{
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegister()
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }
    }
}