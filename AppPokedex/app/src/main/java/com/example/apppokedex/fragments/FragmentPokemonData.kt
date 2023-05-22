package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.PokemonUserDao
import com.google.android.material.snackbar.Snackbar

class FragmentPokemonData : Fragment() {

    private var db: AppDatabase? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    private lateinit var labelName : TextView
    private lateinit var labelMote : TextView
    private lateinit var labelId : TextView
    private lateinit var labelLvl : TextView
    private lateinit var labelTipo : TextView
    private lateinit var labelDebilidad : TextView
    private lateinit var imgPokemon : ImageView
    private lateinit var labelHabilidad : TextView

    lateinit var vista : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokemon_data, container, false)

        labelLvl = vista.findViewById(R.id.txtPokeLvlDato)
        labelMote = vista.findViewById(R.id.txtPokeMoteDato)
        labelId = vista.findViewById(R.id.txtPokeIdDato)
        labelName = vista.findViewById(R.id.txtPokeName)
        labelTipo = vista.findViewById(R.id.txtPokeTipoDato)
        labelHabilidad = vista.findViewById(R.id.txtPokeHabilidadDato)
        labelDebilidad = vista.findViewById(R.id.txtPokeDebilidadDato)
        imgPokemon = vista.findViewById(R.id.imgPokeDato)

        return vista
    }

    override fun onStart() {
        super.onStart()

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getString("UserID", "")

        db = AppDatabase.getInstance(vista.context)
        pokemonDao = db?.pokemonDao()
        pokemonUserDao = db?.pokemonUserDao()

        val idPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).idPokemon
        val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
        val pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser.toString(),idPokemon)

        if(pokemon != null && pokemonUser != null)
        {
            labelLvl.text = pokemonUser.nivel.toString()
            labelMote.text = pokemonUser.mote
            labelId.text =pokemon.idPokemon.toString()
            labelName.text = pokemon.nombre
            labelTipo.text =pokemon.tipo
            labelHabilidad.text = pokemon.habilidad
            labelDebilidad.text = pokemon.debilidad

            Glide.with(vista).load(pokemon.imgURL).into(imgPokemon)


            labelMote.setOnClickListener {
                showAlertDialogMote(pokemonUserDao,idUser.toString(),idPokemon)
            }
            labelLvl.setOnClickListener {
                showAlertDialogLvl(pokemonUserDao,idUser.toString(),idPokemon)
            }
        }
    }

    private fun showAlertDialogMote(pokemonUserDao: PokemonUserDao?, idUser: String?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelMote.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Mote")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelMote.text = newText

            val pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser!!,idPokemon)
            if (pokemonUser != null) {
                pokemonUser.mote = newText
                pokemonUserDao.updatePokemonUser(pokemonUser)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }

    private fun showAlertDialogLvl(pokemonUserDao: PokemonUserDao?, idUser: String?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelLvl.text)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.keyListener = DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Lvl")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelLvl.text = newText

            val pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser!!,idPokemon)
            if (pokemonUser != null) {
                pokemonUser.nivel = newText.toInt()
                pokemonUserDao.updatePokemonUser(pokemonUser)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }

}
