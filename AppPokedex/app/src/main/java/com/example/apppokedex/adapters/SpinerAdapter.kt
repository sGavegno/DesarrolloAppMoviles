package com.example.apppokedex.adapters

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SpinnerAdapter
import com.bumptech.glide.Glide
import com.example.apppokedex.R
import com.example.apppokedex.fragments.FragmentPokedex

class SpinerTipoAdapter(
    private var spinerList: List<Int>,
    private val listener: FragmentPokedex          //Funcion como parametro
    ): SpinnerAdapter {

    private var dataSetObserver: DataSetObserver? = null

    interface SpinerTipoAdapterListener {
        fun onCardViewClick(idTipo: Int, position: Int)
    }

    private fun getImgTipo(idTipo : Int): Int{
        when(idTipo){
            0->{
                return 0
            }
            1->{
                return R.drawable.tipo_normal
            }
            2->{
                return R.drawable.tipo_lucha
            }
            3->{
                return R.drawable.tipo_volador
            }
            4->{
                return R.drawable.tipo_veneno
            }
            5->{
                return R.drawable.tipo_tierra
            }
            6->{
                return R.drawable.tipo_roca
            }
            7->{
                return R.drawable.tipo_bicho
            }
            8->{
                return R.drawable.tipo_fantasma
            }
            9->{
                return R.drawable.tipo_acero
            }
            10->{
                return R.drawable.tipo_fuego
            }
            11->{
                return R.drawable.tipo_agua
            }
            12->{
                return R.drawable.tipo_planta
            }
            13->{
                return R.drawable.tipo_electrico
            }
            14->{
                return R.drawable.tipo_psiquico
            }
            15->{
                return R.drawable.tipo_hielo
            }
            16->{
                return R.drawable.tipo_dragon
            }
            17->{
                return R.drawable.tipo_siniestro
            }
            18->{
                return R.drawable.tipo_hada
            }
        }
        return 0
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        dataSetObserver = p0
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        dataSetObserver = null
    }

    override fun getCount(): Int {
        return spinerList.size
    }

    override fun getItem(p0: Int): Any {
        return spinerList[p0]
    }

    override fun getItemId(p0: Int): Long {
        // Obtener el ID único del elemento en la posición especificada
        return spinerList[p0].toLong()
    }

    override fun hasStableIds(): Boolean {
        return true // Indica que los IDs son estables
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val vista = p1 ?: LayoutInflater.from(p2?.context).inflate(R.layout.item_spiner, p2, false)

        val imgTipo = getImgTipo(spinerList[p0])
        val imgPoke = vista.findViewById<ImageView>(R.id.imgTipoSpiner)
        if (imgTipo != 0){
            Glide.with(vista).load(imgTipo).into(imgPoke)
        }else{
            Glide.with(vista).load(R.drawable.icono_filtro).into(imgPoke)
        }
        return vista
    }

    override fun getItemViewType(p0: Int): Int {
        // Devolver el tipo de vista correspondiente al elemento en la posición especificada
        return spinerList[p0]
    }

    override fun getViewTypeCount(): Int {
        return 1 // Solo hay un tipo de vista en este ejemplo
    }

    override fun isEmpty(): Boolean {
        return spinerList.isEmpty()
    }

    override fun getDropDownView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val vista = p1 ?: LayoutInflater.from(p2?.context).inflate(R.layout.item_spiner, p2, false)

        val imgTipo = getImgTipo(spinerList[p0])
        val imgPoke = vista.findViewById<ImageView>(R.id.imgTipoSpiner)
        if (imgTipo != 0){
            Glide.with(vista).load(imgTipo).into(imgPoke)
        }else{
            Glide.with(vista).load(R.drawable.icono_filtro).into(imgPoke)
        }

        return vista
    }

}