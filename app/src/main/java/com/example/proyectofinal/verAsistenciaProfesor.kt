package com.example.proyectofinal

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ver_asistencia_profesor.lista2
import kotlinx.android.synthetic.main.activity_ver_cursos.lista
import kotlinx.android.synthetic.main.elementosasistencia.imgCurso
import org.json.JSONArray
import org.json.JSONException
import java.nio.BufferUnderflowException

class verAsistenciaProfesor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_asistencia_profesor)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            val idCurso = bundle.getString("id").toString()

            cargarLista(idCurso)
        }




    }
    fun cargarLista(x:String) {
        lista2.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista2.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Asistencia>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/api/admin/asistencia"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val curso =
                                response.getJSONObject(i).getString("curso")
                            if(x == curso){
                                val id =
                                    response.getJSONObject(i).getString("id")
                                val fecha =
                                    response.getJSONObject(i).getString("fecha")
                                val estado =
                                    response.getJSONObject(i).getString("estado")
                                val alumno =
                                    response.getJSONObject(i).getString("alumno")
                                var nombre = ""

                                AppData.listita?.forEach{ (clave, valor)->
                                    if (clave.toString() == alumno){
                                        nombre = valor

                                        return@forEach
                                    }

                                }

                                llenarLista.add(Asistencia(id.toInt(),fecha,estado,nombre,alumno,curso.toInt()))

                            }


                        }
                        val adapter = AdaptadorAsistencia(llenarLista)
                        lista2.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }


}
