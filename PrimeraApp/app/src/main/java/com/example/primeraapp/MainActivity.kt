package com.example.primeraapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View

class MainActivity : AppCompatActivity() {
    lateinit var label : TextView           // Nombre : Tipo  (var indica que es una variable)
                                            // lateinit: indica que lo voy a inicializar despues
    lateinit var btnShow : Button
    lateinit var btnClear : Button
    lateinit var btnColor : Button

    var labelText : String = "Hello World"
    //var labelText2 : String? = null       // con el ? indico que puede ser null

    //Crea la pantalla. Por cada pantalla. voy a tener que llamar a esta funcion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)              //Busca el xml y lo pone en pantalla

        val rootView = findViewById<View>(android.R.id.content) // Obtener la vista raíz de la actividad
        rootView.setBackgroundColor(Color.WHITE) // Establecer el color de fondo a Blanco

        label = findViewById(R.id.txtLable)                 //
        btnShow = findViewById(R.id.btnWrite)
        btnClear = findViewById(R.id.btnClear)
        btnColor = findViewById(R.id.btnColor)

        var btnRed = true // variable booleana para controlar si el texto del botón es rojo o azul

        btnShow.text = "Escribir"
        btnClear.text = "Borrar"
        btnColor.visibility = View.INVISIBLE // Hace que el botón sea invisible

        label.text = ""
        btnShow.setOnClickListener{
            label.text = labelText
            label.setTextColor(Color.BLACK)  // cambia el color del texto a Negro
            btnColor.visibility = View.VISIBLE // Hace que el botón sea visible
            btnColor.text = "Cambiar a Azul" // cambia el texto del botón
            btnRed = true
        }
        btnClear.setOnClickListener{
            label.text = ""
            btnColor.visibility = View.INVISIBLE // Hace que el botón sea invisible
        }
        btnColor.setOnClickListener{
            if (btnRed) {
                label.setTextColor(Color.BLUE)   // cambia el color del texto a Azul
                btnColor.text = "Cambiar a Rojo" // cambia el texto del botón
            } else {
                label.setTextColor(Color.RED)  // cambia el color del texto a Rojo
                btnColor.text = "Cambiar a Azul" // cambia el texto del botón
            }
            btnRed = !btnRed
        }
    }
}

