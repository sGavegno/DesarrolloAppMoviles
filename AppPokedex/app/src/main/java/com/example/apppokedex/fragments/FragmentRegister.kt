package com.example.apppokedex.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuario
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRegister : Fragment() {

    val viewModel: FragmentRegisterViewModel by viewModels()

    lateinit var vista : View

    lateinit var txtUserName : EditText
    lateinit var txtNombre : EditText
    lateinit var txtApellido : EditText
    lateinit var txtEmail : EditText
    lateinit var txtPassword : EditText
    lateinit var txtPasswordConf : EditText
    lateinit var btnSingIn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_register, container, false)
        txtUserName = vista.findViewById(R.id.txtEditRegUserName)
        txtNombre = vista.findViewById(R.id.txtEditRegNombre)
        txtApellido = vista.findViewById(R.id.txtEditRegApellido)
        txtEmail = vista.findViewById(R.id.txtEditRegEmail)
        txtPassword = vista.findViewById(R.id.txtEditRegPassword)
        txtPasswordConf = vista.findViewById(R.id.txtEditRegPasswordConf)
        btnSingIn = vista.findViewById(R.id.btnRegistro)
        return vista
    }

    override fun onStart() {
        super.onStart()


        btnSingIn.setOnClickListener{
            val userNew = Usuario(
                "",
                txtUserName.text.toString(),
                txtPassword.text.toString(),
                txtNombre.text.toString(),
                txtApellido.text.toString(),
                txtEmail.text.toString(),
                "",
                "",
                emptyList()
            )

            val inputTxtPassConf : String = txtPasswordConf.text.toString()
            if(userNew.password == inputTxtPassConf) {
                //Analizo si los paraetros estan en la base de datos
                viewModel.addUser(userNew)
            } else {
                Snackbar.make(vista, "La contraseña no coincide", Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.state.observe(this){
            when(it){
                State.SUCCESS ->{
                    val intent = Intent(activity, activity_home::class.java)
                    startActivity(intent)
                }
                State.FAILURE ->{
                    Snackbar.make(vista, "El usuario ya existe", Snackbar.LENGTH_SHORT).show()
                }
                State.LOADING ->{
                    Snackbar.make(vista, "Analizando", Snackbar.LENGTH_SHORT).show()
                }
                null ->{

                }
            }
        }
    }
}