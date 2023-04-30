package com.example.apppokedex.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.User
import com.google.android.material.snackbar.Snackbar

class FragmentLogin : Fragment() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null

    lateinit var imgTitulo : ImageView
    lateinit var inputTxtEmail : EditText
    lateinit var inputTxtPass : EditText
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
        inputTxtEmail = vista.findViewById(R.id.editTxtEmail)
        inputTxtPass = vista.findViewById(R.id.editTxtPass)
        return vista
    }

    override fun onStart() {
        super.onStart()

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()

        // Dummy call to pre-populate db
        var userTest = userDao?.fetchAllUsers()
        pokemonDao?.fetchAllPokemon()

        btnNexScreen.setOnClickListener{
            //Analizo si los paraetros estan en la base de datos
            val inputTxtUserName : String = inputTxtEmail.text.toString()
            val inputTxtPass : String = inputTxtPass.text.toString()

            //val userFind = users.find { it.email == inputTxt1 && it.password == inputTxt2}
            val userFind = userDao?.fetchUserByUserName(inputTxtUserName)

            if (userFind != null) {
                if(userFind.password == inputTxtPass){

                    val intent = Intent(activity, activity_home::class.java)
                    intent.putExtra("id", userFind.id)
                    startActivity(intent)
                } else {
                    Snackbar.make(vista, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(vista, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnSingin.setOnClickListener{
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegister()
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }
    }

}