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
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.Pokemons
import com.google.android.material.snackbar.Snackbar

class FragmentUser : Fragment(),InputDialogListener {

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
        }
    }

    //Funciones del boton add pokemon
    private fun onClick() {
        showInputDialog()
    }

    private fun showInputDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Confirmar Password")
        val input = EditText(requireContext())
        alertDialog.setView(input)
        alertDialog.setPositiveButton("OK") { _, _ ->
            val password = input.text.toString().toInt()
            inputDialogListener?.onInputDone(password)
            refresh()
        }
        alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
            refresh()
        }
        alertDialog.show()
    }

    override fun onInputDone(input: Int) {
        TODO("Not yet implemented")
    }

    override fun onInputStringDone(input: String) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)

        var user = userDao?.fetchUserById(idUser)
        if(user != null)
        {
            user.name = inputTxtNombre.text.toString()
            user.lastName = inputTxtApellido.text.toString()
            user.email = inputTxtEmail.text.toString()
            user.password = inputTxtPass.text.toString()
            user.direccion = inputTxtDireccion.text.toString()
            user.telefono = inputTxtTelefono.text.toString()
            userDao?.updateUser(user)
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }

        //val action = FragmentPokedexDirections.actionFragmentPokedexToFragmentPokedexData(input)
        //findNavController().navigate(action)            //accion de cambiar de pantalla
    }

    fun refresh() {
        // Crear una nueva instancia del fragment
        val newFragment = FragmentUser()

        // Reemplazar el fragment actual por la nueva instancia
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentUser, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}