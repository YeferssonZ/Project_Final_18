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
import com.example.proyectofinal.R
import kotlinx.android.synthetic.main.activity_informacion_usuario.lblNombre
import kotlinx.android.synthetic.main.activity_informacion_usuario.pApellido
import kotlinx.android.synthetic.main.activity_informacion_usuario.pCelular
import kotlinx.android.synthetic.main.activity_informacion_usuario.pEmail
import kotlinx.android.synthetic.main.activity_informacion_usuario.textView14
import kotlinx.android.synthetic.main.activity_informacion_usuario.textView15
import org.json.JSONArray
import org.json.JSONObject

class InformacionProfesorActivity : AppCompatActivity() {
    private lateinit var txtNombre: TextView
    private lateinit var txtContraseña: TextView
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_profesor)
        val idUsuario = AppData.appId
        obtenerUsuario(idUsuario)
    }
    private fun obtenerUsuario(cuentaId: Int) {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/profesor/"

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
