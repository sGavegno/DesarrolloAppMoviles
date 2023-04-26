package com.example.apppokedex.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.User
import com.google.android.material.snackbar.Snackbar

class FragmentRegister : Fragment() {
    var users : MutableList<User> = mutableListOf()

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    lateinit var vista : View

    lateinit var txtTitulo : TextView
    lateinit var txtUserName : TextView
    lateinit var txtPassword : TextView
    lateinit var txtPasswordConf : TextView
    lateinit var btnSingIn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        users.add(User(1, "Seba", "1234","Sebastian", "Gavgeno", "sgavegno@frba.utn.edu.ar","01155555555", "Av. Medrano 951"))
        users.add(User(2,"Seba","5678","Tester",  "Primero", "test1@frba.utn.edu.ar","01155555555", "Av. Medrano 951"))
        users.add(User(3,"Seba","1357","Tester", "Segundo",  "test2@frba.utn.edu.ar","01155555555", "Av. Medrano 951"))
        users.add(User(4,"Seba","2468","Tester", "Tercero",  "test3@frba.utn.edu.ar","01155555555", "Av. Medrano 951"))

        vista = inflater.inflate(R.layout.fragment_fragment_register, container, false)
        txtTitulo = vista.findViewById(R.id.txtTituloRegistro)
        txtUserName = vista.findViewById(R.id.txtEditRegUserName)
        txtPassword = vista.findViewById(R.id.txtEditRegPassword)
        txtPasswordConf = vista.findViewById(R.id.txtEditRegPasswordConf)
        btnSingIn = vista.findViewById(R.id.btnRegistro)
        return vista
    }

    override fun onStart() {
        super.onStart()

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()

        btnSingIn.setOnClickListener{
            //Analizo si los paraetros estan en la base de datos
            val inputTxtUserName : String = txtUserName.text.toString()
            val inputTxtPass : String = txtPassword.text.toString()
            val inputTxtPassConf : String = txtPasswordConf.text.toString()

            if(inputTxtPass == inputTxtPassConf){
                //Buscar si exciste en la base de datos
                val userFind = userDao?.fetchUserByUserName(inputTxtUserName)

                if (userFind == null) {
                    //si no encuentra este usuario Agregarlo a la base de datos y Cambiar a la Activity
                    userDao?.insertUser(User(0, inputTxtUserName, inputTxtPass, "", "", "", "",""))
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