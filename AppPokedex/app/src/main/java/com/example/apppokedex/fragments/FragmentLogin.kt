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
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.User
import com.google.android.material.snackbar.Snackbar

class FragmentLogin : Fragment() {
    var users : MutableList<User> = mutableListOf()

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

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

        users.add(User(1,"Sebastian", "Gavgeno", "sgavegno@frba.utn.edu.ar",  "1234", "01155555555", "Av. Medrano 951"))
        users.add(User(2,"Sebastian",  "Gavgeno", "a",  "1", "01155555555", "Av. Medrano 951"))
        users.add(User(3,"Tester", "Segundo",  "test2@frba.utn.edu.ar",  "1357", "01155555555", "Av. Medrano 951"))
        users.add(User(4,"Tester", "Tercero",  "test3@frba.utn.edu.ar",  "2468", "01155555555", "Av. Medrano 951"))

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
        btnNexScreen.setOnClickListener{
            //Analizo si los paraetros estan en la base de datos
            val inputTxt1 : String = inputTxtEmail.text.toString()
            val inputTxt2 : String = inputTxtPass.text.toString()

            val userFind = users.find { it.email == inputTxt1 && it.password == inputTxt2}

            if (userFind != null) {
                val intent = Intent(activity, activity_home::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(vista, "datos incorrectos", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnSingin.setOnClickListener{
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegister()
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }
    }

}