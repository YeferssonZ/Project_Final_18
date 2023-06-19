package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_cursos.*
import org.json.JSONException
import java.util.regex.Pattern

class ListadoCursos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cursoAdapter: CursoAdapter
    private var alumnoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_cursos)

        alumnoId = intent.getIntExtra("alumnoId", -1)
        Log.d("AlumnoId", "Valor de alumnoId: $alumnoId")

        recyclerView = findViewById(R.id.listaCursos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cursoAdapter = CursoAdapter(alumnoId)
        recyclerView.adapter = cursoAdapter

        obtenerCursos()

        btnSalirOption.setOnClickListener {
            val salir = Intent(this, AlumnoActivity::class.java)
            startActivity(salir)
            finish()
        }
    }

    private fun obtenerCursos() {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/inscripcion/?alumno=$alumnoId"

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val cursos = ArrayList<AppCurso>()

                try {
                    for (i in 0 until response.length()) {
                        val cursoJson = response.getJSONObject(i)
                        val id = cursoJson.getInt("id")
                        val alumno = cursoJson.getInt("alumno")
                        val curso = cursoJson.getInt("curso")

                        // Obtener el nombre del alumno
                        val nombreAlumno = obtenerNombreAlumno(alumno)

                        // Obtener el nombre del curso
                        val nombreCurso = obtenerNombreCurso(curso)

                        // Crea un objeto Curso con los datos obtenidos
                        val cursoObj = AppCurso(id, alumno, curso, nombreAlumno, nombreCurso)
                        cursos.add(cursoObj)
                    }

                    // Pasa la lista de cursos al adaptador
                    cursoAdapter.setCursos(cursos)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al obtener los cursos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }

    private fun obtenerNombreAlumno(alumnoId: Int): String {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/alumno/$alumnoId/"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val nombre = response.getString("nombre")
                    // Actualiza el valor del nombre del alumno en el adaptador
                    cursoAdapter.actualizarNombreAlumno(alumnoId, nombre)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al obtener el nombre del alumno: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
        // Devuelve un valor por defecto mientras se espera la respuesta de la solicitud
        return ""
    }

    private fun obtenerNombreCurso(cursoId: Int): String {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/curso/$cursoId/"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val nombre = response.getString("nombre")
                    // Actualiza el valor del nombre del curso en el adaptador
                    cursoAdapter.actualizarNombreCurso(cursoId, nombre)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al obtener el nombre del curso: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
        // Devuelve un valor por defecto mientras se espera la respuesta de la solicitud
        return ""
    }

}


//val url = "$urlAPI//api/admin/inscripcion/?alumno=$alumnoId"