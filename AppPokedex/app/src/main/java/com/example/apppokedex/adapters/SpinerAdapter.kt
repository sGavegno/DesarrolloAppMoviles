package com.example.apppokedex.adapters

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SpinnerAdapter
import androidx.cardview.widget.CardView
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
        var tipo = 0
        when(idTipo){
            0->{

            }
            1->{
                tipo = R.drawable.tipo_normal
            }
            2->{
                tipo = R.drawable.tipo_lucha
            }
            3->{
                tipo = R.drawable.tipo_volador
            }
            4->{
                tipo = R.drawable.tipo_veneno
            }
            5->{
                tipo = R.drawable.tipo_tierra
            }
            6->{
                tipo = R.drawable.tipo_roca
            }
            7->{
                tipo = R.drawable.tipo_bicho
            }
            8->{
                tipo = R.drawable.tipo_fantasma
            }
            9->{
                tipo = R.drawable.tipo_acero
            }
            10->{
                tipo = R.drawable.tipo_fuego
            }
            11->{
                tipo = R.drawable.tipo_agua
            }
            12->{
                tipo = R.drawable.tipo_planta
            }
            13->{
                tipo = R.drawable.tipo_electrico
            }
            14->{
                tipo = R.drawable.tipo_psiquico
            }
            15->{
                tipo = R.drawable.tipo_hielo
            }
            16->{
                tipo = R.drawable.tipo_dragon
            }
            17->{
                tipo = R.drawable.tipo_siniestro
            }
            18->{
                tipo = R.drawable.tipo_hada
            }
        }
        return tipo
    }

    class SpinerTipoHolder(v: View){
        private var view :View
        init {
            this.view = v
        }

        @SuppressLint("SetTextI18n")
        fun setAdapter(idTipo: Int){
            //setTipo
            val imgTipo = getImgTipo(idTipo)
            setImagen(imgTipo)
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardSpiner)
        }

        fun setImagen(imagen : Int) {
            val imgPoke = view.findViewById<ImageView>(R.id.imgTipoSpiner)
            Glide.with(view).load(imagen).into(imgPoke)
        }

        private fun getImgTipo(idTipo : Int): Int{
            var tipo = 0
            when(idTipo){
                0->{

                }
                1->{
                    tipo = R.drawable.tipo_normal
                }
                2->{
                    tipo = R.drawable.tipo_lucha
                }
                3->{
                    tipo = R.drawable.tipo_volador
                }
                4->{
                    tipo = R.drawable.tipo_veneno
                }
                5->{
                    tipo = R.drawable.tipo_tierra
                }
                6->{
                    tipo = R.drawable.tipo_roca
                }
                7->{
                    tipo = R.drawable.tipo_bicho
                }
                8->{
                    tipo = R.drawable.tipo_fantasma
                }
                9->{
                    tipo = R.drawable.tipo_acero
                }
                10->{
                    tipo = R.drawable.tipo_fuego
                }
                11->{
                    tipo = R.drawable.tipo_agua
                }
                12->{
                    tipo = R.drawable.tipo_planta
                }
                13->{
                    tipo = R.drawable.tipo_electrico
                }
                14->{
                    tipo = R.drawable.tipo_psiquico
                }
                15->{
                    tipo = R.drawable.tipo_hielo
                }
                16->{
                    tipo = R.drawable.tipo_dragon
                }
                17->{
                    tipo = R.drawable.tipo_siniestro
                }
                18->{
                    tipo = R.drawable.tipo_hada
                }
            }
            return tipo
        }

    }
/*
    override fun onBindViewHolder(holder: SpinerTipoHolder, position: Int) {
        val item = spinerList[position]
        holder.setAdapter(item)
        holder.getCard().setOnClickListener {
            spinerList[position].let { it1 -> listener.onCardViewClick(it1,position) }
        }
    }
*/
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