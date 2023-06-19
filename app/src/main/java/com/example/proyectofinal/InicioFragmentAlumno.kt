package com.example.proyectofinal

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Calendar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragmentAlumno.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragmentAlumno : Fragment() {
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

        return inflater.inflate(R.layout.fragment_inicio_alumno, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sun: ImageView
        var dayland: ImageView
        var nightland: ImageView
        var daySky: View
        var nightSky: View
        sun = view.findViewById(R.id.sun);
        dayland = view.findViewById(R.id.day_landscape);
        nightland = view.findViewById(R.id.night_landscape);
        daySky = view.findViewById(R.id.day_bg);
        nightSky = view.findViewById(R.id.night_bg);
        sun.translationY = -110f
        val textViewHora: TextView = view.findViewById(R.id.Hora)

        val handler = Handler()
        val timeRunnable = object : Runnable {
            override fun run() {
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val second = calendar.get(Calendar.SECOND)

                val formattedTime = String.format("%02d:%02d:%02d", hour, minute, second)
                textViewHora.text = formattedTime

                handler.postDelayed(this, 1000)
            }
        }

        handler.post(timeRunnable)

        val calendar: Calendar = Calendar.getInstance()
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

// Realizar acciones basadas en la hora actual

// Realizar acciones basadas en la hora actual
        if (hour >= 6 && hour < 18) {
            // Es de día (6:00am a 5:59pm)
            // Realiza la funcionalidad correspondiente al día
            sun.animate().translationY(-110f).duration = 1000
            dayland.animate().alpha(1f).duration = 1300
            daySky.animate().alpha(1f).duration = 1300
        } else {
            // Es de noche (6:00pm a 5:59am)
            // Realiza la funcionalidad correspondiente a la noche
            sun.animate().translationY(110f).duration = 1000
            dayland.animate().alpha(0f).duration = 1300
            daySky.animate().alpha(0f).duration = 1300
        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InicioFragmentAlumno.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InicioFragmentAlumno().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}