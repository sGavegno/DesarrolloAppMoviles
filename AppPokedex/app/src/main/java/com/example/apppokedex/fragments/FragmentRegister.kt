package com.example.apppokedex.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.entities.User
import com.google.android.material.snackbar.Snackbar

class FragmentRegister : Fragment() {
    var users : MutableList<User> = mutableListOf()

    lateinit var vista : View

    lateinit var txtTitulo : TextView
    lateinit var txtEmail : TextView
    lateinit var txtPassword : TextView
    lateinit var txtPasswordConf : TextView
    lateinit var btnSingIn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        users.add(User("Sebastian", "Gavgeno", "sgavegno@frba.utn.edu.ar",  "1234", "01155555555", "Av. Medrano 951"))
        users.add(User("Tester",  "Primero", "test1@frba.utn.edu.ar",  "5678", "01155555555", "Av. Medrano 951"))
        users.add(User("Tester", "Segundo",  "test2@frba.utn.edu.ar",  "1357", "01155555555", "Av. Medrano 951"))
        users.add(User("Tester", "Tercero",  "test3@frba.utn.edu.ar",  "2468", "01155555555", "Av. Medrano 951"))

        vista = inflater.inflate(R.layout.fragment_fragment_register, container, false)
        txtTitulo = vista.findViewById(R.id.txtTituloRegistro)
        txtEmail = vista.findViewById(R.id.txtEditRegEmail)
        txtPassword = vista.findViewById(R.id.txtEditRegPassword)
        txtPasswordConf = vista.findViewById(R.id.txtEditRegPasswordConf)
        btnSingIn = vista.findViewById(R.id.btnRegistro)
        return vista
    }

    override fun onStart() {
        super.onStart()
        btnSingIn.setOnClickListener{
            //Analizo si los paraetros estan en la base de datos
            val inputTxtEmail : String = txtEmail.text.toString()
            val inputTxtPass : String = txtPassword.text.toString()
            val inputTxtPassConf : String = txtPasswordConf.text.toString()

            if(inputTxtPass == inputTxtPassConf){
                //Buscar si exciste en la base de datos
                val userFind = users.find { it.email == inputTxtEmail}

                if (userFind == null) {
                    //si no encuentra este usuario Agregarlo a la base de datos y Cambiar a la Activity
                    val intent = Intent(activity, activity_home::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(vista, "Usuario ya registrado", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(vista, "La contraseña no coinide", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}