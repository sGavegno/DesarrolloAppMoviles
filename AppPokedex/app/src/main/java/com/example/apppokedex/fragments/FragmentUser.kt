package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.apppokedex.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentUser : Fragment() {

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
        val idUser = sharedPref?.getString("UserID", "")

        val dbFb = Firebase.firestore
        var userFb : DocumentSnapshot?

        dbFb.collection("user")
            .whereEqualTo("id", idUser)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    userFb = documents.documents[0]
                    inputTxtNombre.setText(userFb?.getString("name") )
                    inputTxtApellido.setText(userFb?.getString("lastName"))
                    inputTxtEmail.setText(userFb?.getString("email"))
                    inputTxtPass.setText(userFb?.getString("password"))
                    inputTxtDireccion.setText(userFb?.getString("direccion"))
                    inputTxtTelefono.setText(userFb?.getString("telefono"))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
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
            showAlertDialogConfigPasword(idUser.toString())
        }
    }

    //Funciones del boton actualizar
    private fun showAlertDialogConfigPasword( idUser: String) {
        val dbFb = Firebase.firestore
        var userFb : DocumentSnapshot?

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

            dbFb.collection("user")
                .whereEqualTo("id", idUser)
                .get()
                .addOnSuccessListener { documents ->
                    if(!documents.isEmpty){
                        userFb = documents.documents[0]
                        val password = userFb?.getString("password")
                        if(password == newText){
                            dbFb.collection("user").document( idUser)
                                .update(
                                    mapOf(
                                        "password" to inputTxtPass.text.toString(),
                                        "name" to inputTxtNombre.text.toString(),
                                        "lastName" to inputTxtApellido.text.toString(),
                                        "email" to inputTxtEmail.text.toString(),
                                        "telefono" to inputTxtTelefono.text.toString(),
                                        "direccion" to inputTxtDireccion.text.toString(),
                                    ),
                                )
                            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
                        } else {
                            Snackbar.make(vista, "Clave Incorrecta", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Firebase", "Error getting documents: ", exception)
                }
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
}