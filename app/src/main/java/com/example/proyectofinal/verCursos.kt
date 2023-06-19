package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ver_cursos.lista
import kotlinx.android.synthetic.main.elementoslista.imgCurso
import org.json.JSONException

class verCursos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_cursos)
        cargarLista()


    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }
    fun cargarLista() {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Elementos>()
        AsyncTask.execute {
            val idProfesor = AppData.claveProfesor.toString()
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/api/admin/curso"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val profesor =
                                response.getJSONObject(i).getString("profesor")
                            if(idProfesor == profesor){
                                val id =
                                    response.getJSONObject(i).getString("id")
                                val nombre =
                                    response.getJSONObject(i).getString("nombre")
                                val ciclo =
                                    response.getJSONObject(i).getString("ciclo")

                                val aulas =
                                    response.getJSONObject(i).getString("aulas")

                                val cursoId = response.getJSONObject(i).getInt("id")

                                llenarLista.add(Elementos(id.toInt(),nombre,ciclo.toInt(),profesor.toInt(),aulas.toInt()))

                            }
                            }
                        val adapter = AdaptadorElementos(llenarLista)
                        lista.adapter = adapter
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