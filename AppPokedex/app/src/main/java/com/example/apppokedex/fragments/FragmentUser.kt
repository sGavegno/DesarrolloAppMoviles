package com.example.apppokedex.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.apppokedex.R
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.UserDao
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

        btnActualizar.setOnClickListener{
            //Analizo si los paraetros estan en la base de datos
            val txtNombre : String = inputTxtNombre.text.toString()
            val txtApellido : String = inputTxtApellido.text.toString()
            val txtEmail : String = inputTxtEmail.text.toString()
            val txtPassword : String = inputTxtPass.text.toString()
            val txtDireccion : String = inputTxtDireccion.text.toString()
            val txtTeefono : String = inputTxtTelefono.text.toString()

            //Agregar cuadro para confirmar cambios por contraseña actual
            if (userFind != null) {
                if(txtNombre != ""){
                    userFind.name = txtNombre
                }
                if(txtApellido != ""){
                    userFind.lastName = txtApellido
                }
                if(txtNombre != ""){
                    userFind.email = txtEmail
                }
                if(txtNombre != ""){
                    userFind.password = txtPassword
                }
                if(txtNombre != ""){
                    userFind.direccion = txtDireccion
                }
                if(txtNombre != ""){
                    userFind.telefono = txtTeefono
                }
                userDao?.updateUser(userFind)
                Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}