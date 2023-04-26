package com.example.apppokedex.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.entities.User
import com.google.android.material.snackbar.Snackbar

class FragmentUser : Fragment() {
    private var newUser : User = User( 0,"Sebastian", "Gavegno", "sgavegno@frba.utn.edu.ar", "1234", "01155555555", "Av. Medrano 951")

    lateinit var vista : View

    lateinit var imgUser : ImageView
    lateinit var inputTxtNombre : EditText
    lateinit var inputTxtApellido : EditText
    lateinit var inputTxtEmail : EditText
    lateinit var inputTxtPass : EditText
    lateinit var inputTxtDireccion : EditText
    lateinit var inputTxtTelefono : EditText
    private lateinit var btnActualizar : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_user, container, false)
        imgUser = vista.findViewById(R.id.imgUser)
        btnActualizar = vista.findViewById(R.id.btnUserActualizar)
        inputTxtNombre = vista.findViewById(R.id.txtEditUserNombre)
        inputTxtApellido = vista.findViewById(R.id.txtEditUserApellido)
        inputTxtEmail = vista.findViewById(R.id.txtEditUserEmail)
        inputTxtPass = vista.findViewById(R.id.txtEditUserPassword)
        inputTxtDireccion = vista.findViewById(R.id.txtEditUserDireccion)
        inputTxtTelefono = vista.findViewById(R.id.txtEditUserTelefono)
        return vista
    }

    override fun onStart() {
        super.onStart()

        inputTxtNombre.setText(newUser.name)
        inputTxtApellido.setText(newUser.lastName)
        inputTxtEmail.setText(newUser.email)
        inputTxtPass.setText(newUser.password)
        inputTxtDireccion.setText(newUser.direccion)
        inputTxtTelefono.setText(newUser.telefono)

        btnActualizar.setOnClickListener{
            //Analizo si los paraetros estan en la base de datos
            val txtNombre : String = inputTxtNombre.text.toString()
            val txtApellido : String = inputTxtApellido.text.toString()
            val txtEmail : String = inputTxtEmail.text.toString()
            val txtPassword : String = inputTxtPass.text.toString()
            val txtDireccion : String = inputTxtDireccion.text.toString()
            val txtTeefono : String = inputTxtTelefono.text.toString()

            //Agregar cuadro para confirmar cambios por contraseña actual

            if(txtNombre != ""){
                newUser.name = txtNombre
            }
            if(txtApellido != ""){
                newUser.lastName = txtApellido
            }
            if(txtNombre != ""){
                newUser.email = txtEmail
            }
            if(txtNombre != ""){
                newUser.password = txtPassword
            }
            if(txtNombre != ""){
                newUser.direccion = txtDireccion
            }
            if(txtNombre != ""){
                newUser.telefono = txtTeefono
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }

    }
}