package com.example.apppokedex.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLogin : Fragment() {

    val viewModel: FragmentLoginViewModel by viewModels()

    private lateinit var imgTitulo : ImageView
    private lateinit var inputTxtUser : EditText
    private lateinit var inputTxtPass : EditText
    private lateinit var progressBarLouding : ProgressBar
    private lateinit var btnNexScreen : Button
    private lateinit var btnSingin : Button

    lateinit var vista : View

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_login, container, false)
        imgTitulo = vista.findViewById(R.id.imgLogin)
        btnNexScreen = vista.findViewById(R.id.btnLogin)
        btnSingin = vista.findViewById(R.id.btnSingin)
        inputTxtUser = vista.findViewById(R.id.editTxtEmail)
        inputTxtPass = vista.findViewById(R.id.editTxtPass)
        progressBarLouding = vista.findViewById(R.id.progBarLogin)

        Glide.with(vista).load(R.drawable.pokedex_logo).into(imgTitulo)

        viewModel.state.observe(viewLifecycleOwner){state ->
            when(state){
                State.SUCCESS ->{
                    inputTxtUser.setText("")
                    inputTxtPass.setText("")
                    val intent = Intent(activity, activity_home::class.java)
                    startActivity(intent)
                }
                State.FAILURE ->{
                    inputTxtUser.visibility = View.VISIBLE
                    inputTxtPass.visibility = View.VISIBLE
                    progressBarLouding.visibility = View.INVISIBLE
                    Snackbar.make(vista, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
                }
                State.LOADING ->{
                    inputTxtUser.visibility = View.INVISIBLE
                    inputTxtPass.visibility = View.INVISIBLE
                    progressBarLouding.visibility = View.VISIBLE
                }
                else ->{}
            }
        }

        return vista
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        inputTxtUser.visibility = View.VISIBLE
        inputTxtPass.visibility = View.VISIBLE
        progressBarLouding.visibility = View.INVISIBLE
        inputTxtUser.setText("sgavegno@frba.utn.edu.ar")
        inputTxtPass.setText("123456789")

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putInt("pos_recycler_view_pokedex", 0)
                commit()
            }
        }

        btnNexScreen.setOnClickListener{
            //Analizo si los parametros estan en la base de datos
            viewModel.userLogin(inputTxtUser.text.toString(),inputTxtPass.text.toString())
        }

        btnSingin.setOnClickListener{
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegister()
            findNavController().navigate(action)            //accion de cambiar de pantalla
        }
    }
}