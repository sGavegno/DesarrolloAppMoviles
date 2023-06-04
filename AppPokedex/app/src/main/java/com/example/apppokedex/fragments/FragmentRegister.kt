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
        txtEmail = vista.findViewById(R.id.txtEditRegEmail)
        txtPassword = vista.findViewById(R.id.txtEditRegPassword)
        txtPasswordConf = vista.findViewById(R.id.txtEditRegPasswordConf)
        btnSingIn = vista.findViewById(R.id.btnRegistro)

        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                State.SUCCESS ->{
                    val intent = Intent(activity, activity_home::class.java)
                    startActivity(intent)
                }
                State.FAILURE ->{
                    Snackbar.make(vista, "Error", Snackbar.LENGTH_SHORT).show()
                }
                State.LOADING ->{
                    Snackbar.make(vista, "Analizando", Snackbar.LENGTH_SHORT).show()
                }
                State.USEREXISTS ->{
                    Snackbar.make(vista, "Email ya registrado", Snackbar.LENGTH_SHORT).show()
                }
                State.PASNOTEQUAL ->{
                    Snackbar.make(vista, "La password no coincide", Snackbar.LENGTH_SHORT).show()
                }
                State.PASSLENGTH ->{
                    Snackbar.make(vista, "Tamaño del password incorrecto", Snackbar.LENGTH_SHORT).show()
                }
                State.USERLENGTH ->{
                    Snackbar.make(vista, "Tamaño del UserName incorrecto", Snackbar.LENGTH_SHORT).show()
                }
                else->{}
            }
        }

        return vista
    }

    override fun onStart() {
        super.onStart()

        btnSingIn.setOnClickListener{
            viewModel.regUserAuth( txtUserName.text.toString(), txtEmail.text.toString(), txtPassword.text.toString(), txtPasswordConf.text.toString())
        }
    }
}