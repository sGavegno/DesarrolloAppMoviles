package com.example.apppokedex.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.apppokedex.R
import com.example.apppokedex.activity.activity_home
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.UserDao
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentRegister : Fragment() {
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    lateinit var vista : View

    lateinit var txtUserName : TextView
    lateinit var txtPassword : TextView
    lateinit var txtPasswordConf : TextView
    lateinit var btnSingIn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_register, container, false)
        txtUserName = vista.findViewById(R.id.txtEditRegUserName)
        txtPassword = vista.findViewById(R.id.txtEditRegPassword)
        txtPasswordConf = vista.findViewById(R.id.txtEditRegPasswordConf)
        btnSingIn = vista.findViewById(R.id.btnRegistro)
        return vista
    }

    override fun onStart() {
        super.onStart()

        val dbFb = Firebase.firestore
        var userFb : String? = null

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()

        btnSingIn.setOnClickListener{
            //Analizo si los paraetros estan en la base de datos
            val inputTxtUserName : String = txtUserName.text.toString()
            val inputTxtPass : String = txtPassword.text.toString()
            val inputTxtPassConf : String = txtPasswordConf.text.toString()

            if(inputTxtPass == inputTxtPassConf) {
                //Buscar si exciste en la base de datos
                dbFb.collection("user")
                    .whereEqualTo("userName", inputTxtUserName)
                    .get()
                    .addOnSuccessListener { documents ->
                        if(documents.isEmpty){
                            // Create a new user with a first and last name
                            val user = hashMapOf(
                                "userName" to inputTxtUserName,
                                "password" to inputTxtPass,
                                "name" to "name",
                                "lastName" to "name",
                                "email" to "email",
                                "telefono" to "telefono",
                                "direccion" to "direccion",
                                "permisos" to 0
                            )
                            // Add a new document with a generated ID
                            dbFb.collection("user")
                                .add(user)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                                    userFb = documentReference.id
                                    dbFb.collection("user").document(userFb!!)
                                        .update("id",userFb)
                                        .addOnSuccessListener { Log.d("Firebase", "DocumentSnapshot successfully updated!")
                                            //Cargo el id del usuario registrado
                                            val sharedPref = context?.getSharedPreferences(
                                                getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                                            if (sharedPref != null) {
                                                with (sharedPref.edit()) {
                                                    putString("UserID", userFb)
                                                    commit()
                                                }
                                            }
                                            val intent = Intent(activity, activity_home::class.java)
                                            startActivity(intent)
                                        }
                                        .addOnFailureListener { e -> Log.w("Firebase", "Error updating document", e) }
                                }
                                .addOnFailureListener { e -> Log.w("Firebase", "Error adding document", e) }
                        }
                        else
                        {
                            Snackbar.make(vista, "Usuario ya registrado", Snackbar.LENGTH_SHORT).show()
                            for (document in documents) {
                                Log.d("Firebase", "${document.id} => ${document.data}")
                            }
                        }
                    }
                    .addOnFailureListener { exception -> Log.w("Firebase", "Error getting documents: ", exception) }
            } else {
                Snackbar.make(vista, "La contraseña no coincide", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}