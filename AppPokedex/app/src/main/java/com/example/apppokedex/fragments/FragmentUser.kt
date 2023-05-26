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
import androidx.fragment.app.viewModels
import com.example.apppokedex.R
import com.example.apppokedex.activity.MainActivity
import com.example.apppokedex.entities.State
import com.example.apppokedex.entities.Usuarios
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentUser : Fragment() {

    val viewModel: FragmentUserViewModel by viewModels()

    lateinit var vista : View

    lateinit var imgUser : ImageView
    lateinit var inputTxtNombre : EditText
    lateinit var inputTxtApellido : EditText
    lateinit var inputTxtEmail : EditText
    lateinit var inputTxtPass : EditText
    lateinit var inputTxtDireccion : EditText
    lateinit var inputTxtTelefono : EditText
    private lateinit var btnActualizar : Button
    private lateinit var btnLogOut : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_user, container, false)
        imgUser = vista.findViewById(R.id.imgUser)
        btnActualizar = vista.findViewById(R.id.btnUserActualizar)
        inputTxtNombre = vista.findViewById(R.id.txtEditUserNombre)
        inputTxtApellido = vista.findViewById(R.id.txtEditUserApellido)
        inputTxtEmail = vista.findViewById(R.id.txtEditUserEmail)
        inputTxtPass = vista.findViewById(R.id.txtEditUserPassword)
        inputTxtDireccion = vista.findViewById(R.id.txtEditUserDireccion)
        inputTxtTelefono = vista.findViewById(R.id.txtEditUserTelefono)

        btnLogOut = vista.findViewById(R.id.btnPokedexLogOut)

        return vista
    }

    override fun onStart() {
        super.onStart()

        val user = viewModel.getUserData()
        inputTxtNombre.setText(user.name)
        inputTxtApellido.setText(user.lastName)
        inputTxtEmail.setText(user.email)
        inputTxtPass.setText(user.password)
        inputTxtDireccion.setText(user.direccion)
        inputTxtTelefono.setText(user.telefono)

        viewModel.state.observe(this){state ->
            when(state){
                State.SUCCESS ->{
                    Snackbar.make(vista, "Actualizacion Exitosa", Snackbar.LENGTH_SHORT).show()
                }
                State.FAILURE ->{
                    Snackbar.make(vista, "Actualizacion Fallida", Snackbar.LENGTH_SHORT).show()
                }
                State.LOADING ->{
                    Snackbar.make(vista, "Cargando", Snackbar.LENGTH_SHORT).show()
                }
                null ->{

                }
            }
        }

        btnActualizar.setOnClickListener {
            showAlertDialogConfigPasword(viewModel.getUserId())
        }

        btnLogOut.setOnClickListener{
            //Funcion para deslogearce
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    //Funciones del boton actualizar
    private fun showAlertDialogConfigPasword( idUser: String) {

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
            val password = viewModel.getUserPassword()
            if(password == newText){
                val user = Usuarios(
                    idUser,
                    viewModel.getUserName(),
                    inputTxtPass.text.toString(),
                    inputTxtNombre.text.toString(),
                    inputTxtApellido.text.toString(),
                    inputTxtEmail.text.toString(),
                    inputTxtTelefono.text.toString(),
                    inputTxtDireccion.text.toString(),
                    viewModel.getUserPermisos()
                )
                viewModel.updateUserData(user)
            } else {
                Snackbar.make(vista, "Clave Incorrecta", Snackbar.LENGTH_SHORT).show()
            }
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
}