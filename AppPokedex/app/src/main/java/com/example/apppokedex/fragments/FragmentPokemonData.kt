package com.example.apppokedex.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.adapters.EvolucionesAdapter
import com.example.apppokedex.database.AppDatabase
import com.example.apppokedex.database.PokemonDao
import com.example.apppokedex.database.PokemonUserDao
import com.example.apppokedex.database.UserDao
import com.example.apppokedex.entities.PokemonRepo
import com.example.apppokedex.entities.Pokemons
import com.google.android.material.snackbar.Snackbar

class FragmentPokemonData : Fragment() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    lateinit var labelName : TextView
    lateinit var labelMote : TextView
    lateinit var labelId : TextView
    lateinit var labelLvl : TextView
    lateinit var labelTipo : TextView
    lateinit var labelDebilidad : TextView
    lateinit var imgPokemon : ImageView
    lateinit var labelDescripcion : TextView
    lateinit var labelAltura : TextView
    lateinit var labelPeso : TextView
    lateinit var labelCategoria: TextView
    lateinit var labelHabilidad : TextView
    lateinit var txtEvolucion : TextView
    lateinit var recEvoluciones : RecyclerView
    lateinit var adapter: EvolucionesAdapter

    lateinit var vista : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment_pokemon_data, container, false)
        labelName = vista.findViewById(R.id.txtPokeName)
        labelMote = vista.findViewById(R.id.txtPokeMoteDato)
        labelId = vista.findViewById(R.id.txtPokeIdDato)
        labelLvl = vista.findViewById(R.id.txtPokeLvlDato)
        labelTipo = vista.findViewById(R.id.txtPokeTipoDato)
        labelDebilidad = vista.findViewById(R.id.txtPokeDebilidadDato)
        labelDescripcion = vista.findViewById(R.id.txtPokeDescripcion)
        imgPokemon = vista.findViewById(R.id.imgPokeDato)
        labelAltura = vista.findViewById(R.id.txtPokeAlturaDato)
        labelPeso = vista.findViewById(R.id.txtPokePesoDato)
        labelCategoria = vista.findViewById(R.id.txtPokeCategoriaDato)
        labelHabilidad = vista.findViewById(R.id.txtPokeHabilidadDato)
        txtEvolucion = vista.findViewById(R.id.txtPokeEvolucion)
        recEvoluciones = vista.findViewById(R.id.listaEvolucion)



        return vista
    }

    override fun onStart() {
        super.onStart()

        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser = sharedPref?.getInt("UserID", 0)

        db = AppDatabase.getInstance(vista.context)
        userDao = db?.userDao()
        pokemonDao = db?.pokemonDao()
        pokemonUserDao = db?.pokemonUserDao()

        var PokemonEvolucionList: MutableList<Int> = mutableListOf()

        var idPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).idPokemon
        var pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
        var pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser!!,idPokemon)

        if(pokemon != null && pokemonUser != null)
        {
            labelName.text = pokemon.nombre
            labelMote.text = pokemonUser.mote
            labelId.text =pokemon.idPokemon.toString()
            labelLvl.text = pokemonUser.nivel.toString()
            labelTipo.text =pokemon.tipo
            labelDebilidad.text = pokemon.debilidad
            labelDescripcion.text = pokemonUser.descripcion
            labelAltura.text = pokemonUser.altura
            labelPeso.text = pokemonUser.peso
            labelCategoria.text = pokemon.categoria
            labelHabilidad.text = pokemon.habilidad

            Glide.with(vista).load(pokemon.imgURL).into(imgPokemon)

            if(pokemon.child != 0) {
                var PokemonChild = pokemonDao?.fetchPokemonByIdPokemon(pokemon.child)
                if (PokemonChild != null && PokemonChild.child != 0) {
                    PokemonEvolucionList.add(PokemonChild.child)
                }
                PokemonEvolucionList.add(pokemon.child)
            }
            PokemonEvolucionList.add(pokemon.id)
            if(pokemon.parent != 0) {
                PokemonEvolucionList.add(pokemon.parent)
                var PokemonParent = pokemonDao?.fetchPokemonByIdPokemon(pokemon.parent)
                if (PokemonParent != null && PokemonParent.parent != 0) {
                    PokemonEvolucionList.add(PokemonParent.parent)
                }
            }

            labelAltura.setOnClickListener {
                showAlertDialogAltura(pokemonUserDao,idUser,idPokemon)
            }
            labelPeso.setOnClickListener {
                showAlertDialogPeso(pokemonUserDao,idUser,idPokemon)
            }
            labelMote.setOnClickListener {
                showAlertDialogMote(pokemonUserDao,idUser,idPokemon)
            }
            labelLvl.setOnClickListener {
                showAlertDialogLvl(pokemonUserDao,idUser,idPokemon)
            }
            labelDescripcion.setOnClickListener {
                showAlertDialogDescripcion(pokemonUserDao,idUser,idPokemon)
            }

            adapter = EvolucionesAdapter(PokemonEvolucionList){ position ->
                val idPokemon = PokemonEvolucionList[position]
                val pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser!!,idPokemon)
                if(pokemonUser != null)
                {
                    val action = FragmentPokemonDataDirections.actionFragmentPokemonDataSelf(
                        idPokemon)
                    findNavController().navigate(action)
                } else {
                    Snackbar.make(vista, "El Pokeon no esta en la lista", Snackbar.LENGTH_SHORT).show()
                }
            }
            recEvoluciones.layoutManager = GridLayoutManager(context, PokemonEvolucionList.size)             //da formato a la lista
            recEvoluciones.adapter = adapter
        }
    }

    private fun showAlertDialogMote(pokemonUserDao: PokemonUserDao?, idUser: Int?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelMote.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Altura")
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

    private fun showAlertDialogLvl(pokemonUserDao: PokemonUserDao?, idUser: Int?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelLvl.text)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.keyListener = DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Altura")
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

    private fun showAlertDialogDescripcion(pokemonUserDao: PokemonUserDao?, idUser: Int?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelDescripcion.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Altura")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelDescripcion.text = newText

            val pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser!!,idPokemon)
            if (pokemonUser != null) {
                pokemonUser.descripcion = newText
                pokemonUserDao.updatePokemonUser(pokemonUser)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogAltura(pokemonUserDao: PokemonUserDao?, idUser: Int?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelAltura.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Altura")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelAltura.text = newText

            val pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser!!,idPokemon)
            if (pokemonUser != null) {
                pokemonUser.altura = newText
                pokemonUserDao.updatePokemonUser(pokemonUser)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogPeso(pokemonUserDao: PokemonUserDao?, idUser: Int?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelPeso.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Peso")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelPeso.text = newText
            val pokemonUser = pokemonUserDao?.fetchPokemonUserById(idUser!!,idPokemon)
            if (pokemonUser != null) {
                pokemonUser.peso = newText
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