package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.apppokedex.R
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.InputDialogListener
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.Pokemons
import com.google.android.material.snackbar.Snackbar

class FragmentUser : Fragment() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    lateinit var vista : View

    lateinit var imgUser : ImageView
    lateinit var inputTxtNombre : EditText
    lateinit var inputTxtApellido : EditText
    lateinit var inputTxtEmail : EditText
    lateinit var inputTxtPass : EditText
    lateinit var inputTxtDireccion : EditText
    lateinit var inputTxtTelefono : EditText
    private lateinit var btnActualizar : Button

    private var inputDialogListener: InputDialogListener? = null

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
        return vista
    }

    override fun onStart() {
        super.onStart()
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()

        val userFind = idUser?.let { userDao?.fetchUserById(it) }

        if (userFind != null) {
            inputTxtNombre.setText(userFind.name)
            inputTxtApellido.setText(userFind.lastName)
            inputTxtEmail.setText(userFind.email)
            inputTxtPass.setText(userFind.password)
            inputTxtDireccion.setText(userFind.direccion)
            inputTxtTelefono.setText(userFind.telefono)
        }
/*
        btnActualizar.setOnClickListener{
            if(userFind != null)
            {
                userFind.name = inputTxtNombre.text.toString()
                userFind.lastName = inputTxtApellido.text.toString()
                userFind.email = inputTxtEmail.text.toString()
                userFind.password = inputTxtPass.text.toString()
                userFind.direccion = inputTxtDireccion.text.toString()
                userFind.telefono = inputTxtTelefono.text.toString()
                userDao?.updateUser(userFind)
                Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
            }
        }*/

        btnActualizar.setOnClickListener {
            userDao?.let { it1 -> idUser?.let { it2 -> showAlertDialogConfigPasword(it1, it2) } }
        }

    }

    //Funciones del boton actualizar
    private fun showAlertDialogConfigPasword(userDao: UserDao, idUser: Int) {
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

            val userFind = userDao?.fetchUserById(idUser)
            if (userFind != null) {
                if(userFind.password == newText)
                {
                    userFind.name = inputTxtNombre.text.toString()
                    userFind.lastName = inputTxtApellido.text.toString()
                    userFind.email = inputTxtEmail.text.toString()
                    userFind.password = inputTxtPass.text.toString()
                    userFind.direccion = inputTxtDireccion.text.toString()
                    userFind.telefono = inputTxtTelefono.text.toString()
                    userDao.updateUser(userFind)
                    Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(vista, "Clave Incorrecta", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
}