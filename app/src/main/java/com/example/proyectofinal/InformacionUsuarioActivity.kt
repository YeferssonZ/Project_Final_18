package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_opciones_alumno.txtEmailLogin
import kotlinx.android.synthetic.main.fragment_opciones_alumno.txtUserLogin
import org.json.JSONArray
import kotlinx.android.synthetic.main.activity_informacion_usuario.*

class InformacionUsuarioActivity : AppCompatActivity() {
    private lateinit var txtNombreUsuario: TextView
    private lateinit var txtContraseñaUsuario: TextView
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_usuario)
        val idUsuario = AppData.appId
        obtenerUsuario(idUsuario)


    }
    private fun obtenerUsuario(cuentaId: Int) {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/alumno/"

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val jsonArray = JSONArray(response)



                for (i in 0 until jsonArray.length()) {
                    val alumno = jsonArray.getJSONObject(i)
                    val id = alumno.getInt("cuenta")
                    if (id == cuentaId) {
                        val nombre = alumno.getString("nombre")
                        val apellido = alumno.getString("apellido")
                        val celular = alumno.getString("celular")
                        val email = alumno.getString("email")

                        // Actualizar los TextView con el nombre y el correo del usuario
                        lblNombre.text = nombre
                        pApellido.text = apellido
                        pCelular.text = celular
                        pEmail.text = email
                        textView14.text = nombre
                        textView15.text = email
                        val alumnoId = alumno.getInt("id")
                        break
                    }
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al obtener el usuario: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {}

        queue.add(request)

    }



}
