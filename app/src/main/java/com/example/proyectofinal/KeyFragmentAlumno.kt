package com.example.proyectofinal

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_key_alumno.btnActualizar
import kotlinx.android.synthetic.main.fragment_key_alumno.tv_text
import kotlinx.android.synthetic.main.fragment_key_alumno.txtAlumnoId

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KeyFragmentAlumno.newInstance] factory method to
 * create an instance of this fragment.
 */
class KeyFragmentAlumno : Fragment() {
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
        return inflater.inflate(R.layout.fragment_key_alumno, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordGenerator = PasswordGenerator()
        val claveId = AppData.claveId
        txtAlumnoId.text = claveId.toString()


        btnActualizar.setOnClickListener {
            var password = passwordGenerator.generatePassword(4, "")
            tv_text.text = password
            var a = password
            actualizarClave(claveId, password)
        }
    }
    private fun actualizarClave(claveId: Int, nuevaClave: String) {
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/keyAlumno/$claveId/"

        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Request.Method.PATCH, url,
            Response.Listener { response ->
                // La solicitud se ha realizado correctamente
                Toast.makeText(requireContext(), "Clave actualizada correctamente", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    borrarClave(claveId)
                }, 6000)

                // Aquí puedes manejar la respuesta de la API si es necesario
            },
            Response.ErrorListener { error ->
                // Se produjo un error en la solicitud
                Toast.makeText(requireContext(), "Error al actualizar la clave: ${error.message}", Toast.LENGTH_SHORT).show()

                // Aquí puedes manejar el error de la API si es necesario
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["clave"] = nuevaClave
                return params
            }
        }

        queue.add(request)
    }
    private fun borrarClave(claveId: Int) {
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/keyAlumno/$claveId/"

        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Request.Method.PATCH, url,
            Response.Listener { response ->
                // La solicitud se ha realizado correctamente
                Toast.makeText(requireContext(), "Clave borrada correctamente", Toast.LENGTH_SHORT).show()

                // Actualizar el campo de clave a "null" en la interfaz de usuario
                txtAlumnoId.text = "null"
            },
            Response.ErrorListener { error ->
                // Se produjo un error en la solicitud
                Toast.makeText(requireContext(), "Error al borrar la clave: ${error.message}", Toast.LENGTH_SHORT).show()

                // Aquí puedes manejar el error de la API si es necesario
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["clave"] = ""
                return params
            }
        }

        queue.add(request)
    }
















    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment KeyFragmentAlumno.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KeyFragmentAlumno().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}