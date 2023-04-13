package com.example.navegacion.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.navegacion.R

class Fragment2 : Fragment() {
    lateinit var label : TextView

    lateinit var vista : View

    var arg : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_fragment2, container, false)
        label = vista.findViewById(R.id.txtView2)
        return vista
    }

    override fun onStart() {
        //me traigo el argumento del fragment 1
        //arg = Fragment2Args.fromBundle(requireArguments()).argUser.toString()
        super.onStart()
    }

}