package com.example.apppokedex.fragments

import android.content.Context
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
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    lateinit var vista : View

    lateinit var txtUserName : TextView
    lateinit var txtPassword : TextView
    lateinit var txtPasswordConf : TextView
    lateinit var btnSingIn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_register, container, false)
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
                    userDao?.insertUser(User(0, inputTxtUserName, inputTxtPass, "", "", "", "","",0))
                    val user = userDao?.fetchUserByUserName(inputTxtUserName)
                    //Cargo el id del usuario registrado
                    val sharedPref = context?.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with (sharedPref.edit()) {
                            user?.let { it1 -> putInt("UserID", it1.id) }
                            commit()
                        }
                    }

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