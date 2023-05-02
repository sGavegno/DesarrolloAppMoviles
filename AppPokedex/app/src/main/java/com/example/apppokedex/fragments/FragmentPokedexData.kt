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
import com.google.android.material.snackbar.Snackbar

class FragmentPokedexData : Fragment() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var pokemonDao: PokemonDao? = null
    private var pokemonUserDao: PokemonUserDao? = null

    private lateinit var labelName : TextView
    private lateinit var labelId : TextView
    private lateinit var labelTipo : TextView
    private lateinit var labelGeneracion : TextView
    private lateinit var labelDebilidad : TextView
    private lateinit var imgPokemon : ImageView
    private lateinit var labelDescripcion : TextView
    private lateinit var labelAltura : TextView
    private lateinit var labelPeso : TextView
    private lateinit var labelCategoria: TextView
    private lateinit var labelHabilidad : TextView
    private lateinit var txtEvolucion : TextView
    private lateinit var recEvoluciones : RecyclerView
    private lateinit var adapter: EvolucionesAdapter

    lateinit var vista : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_fragment_pokedex_data, container, false)
        labelName = vista.findViewById(R.id.txtPokedexName)
        labelId = vista.findViewById(R.id.txtPokedexIdDato)
        labelTipo = vista.findViewById(R.id.txtPokedexTipoDato)
        labelGeneracion = vista.findViewById(R.id.txtPokedexGeneracionDato)
        labelDebilidad = vista.findViewById(R.id.txtPokedexDebilidadDato)
        labelDescripcion = vista.findViewById(R.id.txtPokedexDescripcion)
        imgPokemon = vista.findViewById(R.id.imgPokedexDato)
        labelAltura = vista.findViewById(R.id.txtPokedexAlturaDato)
        labelPeso = vista.findViewById(R.id.txtPokedexPesoDato)
        labelCategoria = vista.findViewById(R.id.txtPokedexCategoriaDato)
        labelHabilidad = vista.findViewById(R.id.txtPokedexHabilidadDato)
        txtEvolucion = vista.findViewById(R.id.txtPokdexeEvolucion)
        recEvoluciones = vista.findViewById(R.id.listaPokedexEvolucion)

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

        val PokemonEvolucionList: MutableList<Int> = mutableListOf()

        val idPokemon = FragmentPokemonDataArgs.fromBundle(requireArguments()).idPokemon
        val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)

        if(pokemon != null)
        {
            labelName.text = pokemon.nombre
            labelId.text =pokemon.idPokemon.toString()
            labelTipo.text =pokemon.tipo
            labelGeneracion.text = pokemon.generacion.toString()
            labelDebilidad.text = pokemon.debilidad
            labelDescripcion.text = pokemon.descripcion
            labelAltura.text = pokemon.altura
            labelPeso.text = pokemon.peso
            labelCategoria.text = pokemon.categoria
            labelHabilidad.text = pokemon.habilidad

            Glide.with(vista).load(pokemon.imgURL).into(imgPokemon)
            imgPokemon.tag = pokemon.imgURL

            if(pokemon.child != 0) {
                val PokemonChild = pokemonDao?.fetchPokemonByIdPokemon(pokemon.child)
                if (PokemonChild != null && PokemonChild.child != 0) {
                    PokemonEvolucionList.add(PokemonChild.child)
                }
                PokemonEvolucionList.add(pokemon.child)
            }
            PokemonEvolucionList.add(pokemon.idPokemon)

            if(pokemon.parent != 0) {
                if(pokemon.parent != pokemon.idPokemon){
                    PokemonEvolucionList.add(pokemon.parent)
                    val PokemonParent = pokemonDao?.fetchPokemonByIdPokemon(pokemon.parent)
                    if (PokemonParent != null && PokemonParent.parent != 0) {
                        PokemonEvolucionList.add(PokemonParent.parent)
                    }
                } else {
                    //Tiene más de una segunda evolucion
                    val pokemonChild = pokemonDao?.fetchPokemonByChild(pokemon.idPokemon)
                    if(pokemonChild != null)
                    {
                        for(poke in pokemonChild){
                            poke?.let { PokemonEvolucionList.add(it.idPokemon) }
                        }
                    }
                }
            }

            adapter = EvolucionesAdapter(PokemonEvolucionList){ position ->
                //Guardar datos actualizados
                val idPokemon = PokemonEvolucionList[position]
                val action = FragmentPokedexDataDirections.actionFragmentPokedexDataSelf(
                    idPokemon)
                findNavController().navigate(action)            //accion de cambiar de pantalla
            }
            recEvoluciones.layoutManager = GridLayoutManager(context, PokemonEvolucionList.size)             //da formato a la lista
            recEvoluciones.adapter = adapter

        }

        val user = userDao?.fetchUserById(idUser)
        if(user!!.permisos == 1){       // Solo acciones para usuarios con permismo de supervisor Falta agregar el boton para incluir pokemons

            labelName.setOnClickListener {
                showAlertDialogName(pokemonDao,idPokemon)
            }
            labelId.setOnClickListener {
                showAlertDialogId(pokemonDao,idPokemon)
            }
            labelTipo.setOnClickListener {
                showAlertDialogTipo(pokemonDao,idPokemon)
            }
            labelGeneracion.setOnClickListener{
                showAlertDialogGeneracion(pokemonDao,idPokemon)
            }
            labelDescripcion.setOnClickListener {
                showAlertDialogDescripcion(pokemonDao,idPokemon)
            }
            labelAltura.setOnClickListener {
                showAlertDialogAltura(pokemonDao,idPokemon)
            }
            labelPeso.setOnClickListener {
                showAlertDialogPeso(pokemonDao,idPokemon)
            }
            labelCategoria.setOnClickListener {
                showAlertDialogCategoria(pokemonDao,idPokemon)
            }
            labelHabilidad.setOnClickListener {
                showAlertDialogHabilidad(pokemonDao,idPokemon)
            }
            labelDebilidad.setOnClickListener {
                showAlertDialogDebilidad(pokemonDao,idPokemon)
            }
            imgPokemon.setOnClickListener{
                showAlertDialogImg(pokemonDao,idPokemon)
            }
            txtEvolucion.setOnClickListener {
                if(idPokemon != 0)
                {
                    showAlertDialogEvolucion(pokemonDao,idPokemon)
                }
            }
        }
    }
    private fun showAlertDialogName(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelName.text)

        // Cream un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Nombre")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelName.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.nombre = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogId(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelId.text)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.keyListener = DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Id")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelId.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.idPokemon = newText.toInt()
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogTipo(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelTipo.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Tipo")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelTipo.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.tipo = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogGeneracion(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelGeneracion.text)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.keyListener = DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Generacion")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelGeneracion.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.generacion = newText.toInt()
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogDescripcion(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelDescripcion.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Descripcion")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelDescripcion.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.descripcion = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogAltura(pokemonDao: PokemonDao?, idPokemon: Int) {
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

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.altura = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogPeso(pokemonDao: PokemonDao?, idPokemon: Int) {
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

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.peso = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogCategoria(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelCategoria.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Categoria")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelCategoria.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.categoria = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogHabilidad(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelHabilidad.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Habilidad")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelHabilidad.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.habilidad = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogDebilidad(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(labelDebilidad.text)

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Deblidad")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            labelDebilidad.text = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.debilidad = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogImg(pokemonDao: PokemonDao?, idPokemon: Int) {
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(imgPokemon.tag.toString())

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Deblidad")
        alertDialog.setView(editText)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText y establecerlo en el TextView
            val newText = editText.text.toString()
            Glide.with(vista).load(newText).into(imgPokemon)
            imgPokemon.tag = newText

            val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
            if (pokemon != null) {
                pokemon.imgURL = newText
                pokemonDao.updatePokemon(pokemon)
            }
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog.show()
    }
    private fun showAlertDialogEvolucion(pokemonDao: PokemonDao?, idPokemon: Int) {
        val pokemon = pokemonDao?.fetchPokemonByIdPokemon(idPokemon)
        // Crear un EditText para obtener el nuevo texto
        val editText = EditText(requireContext())
        editText.setText(pokemon?.child.toString())
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.keyListener = DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9

        val editText2 = EditText(requireContext())
        editText2.setText(pokemon!!.parent.toString())
        editText2.inputType = InputType.TYPE_CLASS_NUMBER
        editText2.keyListener = DigitsKeyListener.getInstance("0123456789") // Solo permitir dígitos del 0 al 9

        // Crear un cuadro de texto utilizando un AlertDialog.Builder
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog.setTitle("Modificar Evolucion Previa.")
        alertDialog.setView(editText)

        val alertDialog2 = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialog2.setTitle("Modificar Evolucion Posterior.")
        alertDialog2.setView(editText2)

        // Agregar un botón "Aceptar" al cuadro de texto
        alertDialog.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText
            val newText = editText.text.toString()
            pokemon.child = newText.toInt()
            pokemonDao.updatePokemon(pokemon)
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        alertDialog2.setPositiveButton("Aceptar") { _, _ ->
            // Obtener el nuevo texto del EditText
            val newText2 = editText2.text.toString()
            pokemon.parent = newText2.toInt()
            pokemonDao.updatePokemon(pokemon)
            Snackbar.make(vista, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }
        // Agregar un botón "Cancelar" al cuadro de texto
        alertDialog.setNegativeButton("Cancelar", null)
        alertDialog2.setNegativeButton("Cancelar", null)
        // Mostrar el cuadro de texto
        alertDialog2.show()
        alertDialog.show()
    }
}
