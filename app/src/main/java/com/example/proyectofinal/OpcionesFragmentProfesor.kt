package com.example.proyectofinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_opciones.imgCuarderno
import kotlinx.android.synthetic.main.fragment_opciones.imgSalir
import kotlinx.android.synthetic.main.fragment_opciones.view.imgSalir
import kotlinx.android.synthetic.main.fragment_opciones_alumno.imgCandado
import kotlinx.android.synthetic.main.fragment_opciones_alumno.imgPerfil
import kotlinx.android.synthetic.main.fragment_opciones_alumno.view.imgSalir2
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OpcionesFragmentProfesor.newInstance] factory method to
 * create an instance of this fragment.
 */
class OpcionesFragmentProfesor : Fragment() {
    private lateinit var imgCuaderno: ImageView
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_opciones, container, false)
        val imgCuarderno = view.findViewById<ImageView>(R.id.imgCuarderno)
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Cerrar sesión") { dialog, which ->
                val logout = Intent(activity, LogueoActivity::class.java)
                startActivity(logout)
                activity?.finish()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
            }
            .setCancelable(true)
        view.imgSalir.setOnClickListener{
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        imgCuarderno.setOnClickListener {
            val intent = Intent(activity, verCursos::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgPerfil.setOnClickListener {
            abrirNuevaActividad()
        }


        imgCandado.setOnClickListener {
            mostrarCalendario()
        }

    }
    private fun abrirNuevaActividad() {
        // Código para abrir la nueva actividad
        val intent = Intent(activity, InformacionProfesorActivity::class.java)
        startActivity(intent)
    }
    private fun mostrarCalendario() {
        // Obtener la fecha actual
        val calendario = Calendar.getInstance()
        val año = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val día = calendario.get(Calendar.DAY_OF_MONTH)

        // Crear el DatePickerDialog
        val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
            // Aquí puedes manejar la fecha seleccionada, por ejemplo, mostrarla en un Toast
            val fechaSeleccionada = "$day/${month + 1}/$year"
            Toast.makeText(context, "Fecha seleccionada: $fechaSeleccionada", Toast.LENGTH_SHORT).show()
        }, año, mes, día)

        // Mostrar el DatePickerDialog
        datePickerDialog.show()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OpcionesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OpcionesFragmentProfesor().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}