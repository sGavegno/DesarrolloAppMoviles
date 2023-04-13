package com.example.apppokedex.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.apppokedex.R
import com.example.apppokedex.entities.User
import com.google.android.material.snackbar.Snackbar

class FragmentLogin : Fragment() {
    private var newUser : User = User( "Sebastian", "Gavegno", "sgavegno@frba.utn.edu.ar", "1234")
    var users : MutableList<User> = mutableListOf()

    lateinit var label : TextView
    lateinit var inputTxtEmail : EditText
    lateinit var inputTxtPass : EditText
    private lateinit var btnNexScreen : Button

    lateinit var vista : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        users.add(User("Sebastian", "Gavgeno", "sgavegno@frba.utn.edu.ar",  "1234"))
        users.add(User("Tester",  "Primero", "test1@frba.utn.edu.ar",  "5678"))
        users.add(User("Tester", "Segundo",  "test2@frba.utn.edu.ar",  "1357"))
        users.add(User("Tester", "Tercero",  "test3@frba.utn.edu.ar",  "2468"))

        vista = inflater.inflate(R.layout.fragment_fragment_login, container, false)
        label = vista.findViewById(R.id.txtView)
        btnNexScreen = vista.findViewById(R.id.btnLogin)
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
                println("Bienvenido ${userFind.lastName} ${userFind.name}.")
                //val action = Fragment1Directions.actionFragment1ToFragment2( User())
                val action = FragmentLoginDirections.actionFragmentLoginToFragmentPokedex()
                findNavController().navigate(action)            //accion de cambiar de pantalla
            } else {
                Snackbar.make(vista, "datos incorrectos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}