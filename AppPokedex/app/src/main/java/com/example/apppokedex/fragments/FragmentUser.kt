package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import com.example.apppokedex.R
import com.example.apppokedex.activity.MainActivity
import com.example.apppokedex.entities.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentUser : Fragment() {

    val viewModel: FragmentUserViewModel by viewModels()

    lateinit var vista : View

    lateinit var imgUser : ImageView
    lateinit var inputTxtUserName : EditText
    lateinit var inputTxtNombre : EditText
    lateinit var inputTxtApellido : EditText
    private lateinit var btnActualizar : Button
    private lateinit var btnResetPokedex : Button
    private lateinit var btnLogOut : Button
    private lateinit var progressBarLouding : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_user, container, false)
        imgUser = vista.findViewById(R.id.imgUser)
        btnActualizar = vista.findViewById(R.id.btnUserActualizar)
        inputTxtUserName = vista.findViewById(R.id.txtEditUserName)
        inputTxtNombre = vista.findViewById(R.id.txtEditNombre)
        inputTxtApellido = vista.findViewById(R.id.txtEditApellido)
        btnResetPokedex = vista.findViewById(R.id.btnResetPokedex)
        btnLogOut = vista.findViewById(R.id.btnPokedexLogOut)
        progressBarLouding = vista.findViewById(R.id.progressBarUser)


        viewModel.state.observe(viewLifecycleOwner){state ->
            when(state){
                State.SUCCESS ->{
                    inputTxtUserName.visibility = View.VISIBLE
                    inputTxtNombre.visibility = View.VISIBLE
                    inputTxtApellido.visibility = View.VISIBLE
                    progressBarLouding.visibility = View.INVISIBLE
                }
                State.FAILURE ->{
                    Snackbar.make(vista, "Actualizacion Fallida", Snackbar.LENGTH_SHORT).show()
                }
                State.LOADING ->{
                    inputTxtUserName.visibility = View.INVISIBLE
                    inputTxtNombre.visibility = View.INVISIBLE
                    inputTxtApellido.visibility = View.INVISIBLE
                    progressBarLouding.visibility = View.VISIBLE
                }
                else -> {}
            }
        }

        return vista
    }

    override fun onStart() {
        super.onStart()

        val user = viewModel.getUserData()
        progressBarLouding.visibility = View.INVISIBLE
        inputTxtUserName.setText(user.userName)
        inputTxtNombre.setText(user.name)
        inputTxtApellido.setText(user.lastName)

        btnActualizar.setOnClickListener {
            showAlertDialogConfigPassword()
        }

        btnResetPokedex.setOnClickListener {
            showAlertDialogConfigResetPokemon()
        }

        btnLogOut.setOnClickListener{
            //Funcion para deslogearce
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    //Funciones del boton actualizar
    private fun showAlertDialogConfigPassword() {

        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        // Cream un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Confirmar Passsword")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            val user = viewModel.getUserData()
            user.userName = inputTxtUserName.text.toString()
            user.name = inputTxtNombre.text.toString()
            user.lastName = inputTxtApellido.text.toString()
            viewModel.updateUserData(user.email.toString(), newText, user)
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }

    private fun showAlertDialogConfigResetPokemon() {

        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        // Cream un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Seguro quiere resetear los Pokemons?\n Se requiere confirmacion por password")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            val user = viewModel.getUserData()
            viewModel.removePokemonUser(user.email.toString(), newText, user)
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
}