package com.example.proyectofinal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detalles_curso.*

class DetallesCursoActivity : AppCompatActivity() {
    private lateinit var notasAdapter: NotasAdapter
    private val notasList = ArrayList<AppNota>()
    private var alumnoId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_curso)

        // Obtener el ID del curso seleccionado desde el intent
        val cursoId = intent.getIntExtra("cursoId", -1)

        // Obtener el ID del alumno en el método onCreate()
        alumnoId = obtenerAlumnoId()

        // Configurar el título del curso
        val nombreCurso = intent.getStringExtra("nombreCurso")
        txtTituloCurso.text = nombreCurso

        // Inicializar el RecyclerView y el adaptador de notas
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNotas)
        notasAdapter = NotasAdapter()
        recyclerView.adapter = notasAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener las notas del alumno para el curso seleccionado
        obtenerNotasAlumno(cursoId)
    }

    private fun obtenerNotasAlumno(cursoId: Int) {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/nota/?alumno=$alumnoId&curso=$cursoId"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Procesar la respuesta JSON y actualizar el adaptador de notas
                notasList.clear() // Limpiar la lista antes de agregar las nuevas notas
                val notas = response.getJSONArray("notas")
                for (i in 0 until notas.length()) {
                    val nota = notas.getJSONObject(i)
                    val notaId = nota.getInt("id")
                    val valorNota = nota.getString("nota")
                    val laboratorio = nota.getInt("laboratorio")
                    val alumno = nota.getInt("alumno")

                    val appNota = AppNota(notaId, valorNota, laboratorio, alumno)
                    notasList.add(appNota)
                }

                notasAdapter.setNotas(notasList)
            },
            { error ->
                // Manejar el error de la solicitud
                val errorMessage = error.localizedMessage ?: "Error al obtener las notas del alumno"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            })

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(request)
    }

    private fun obtenerAlumnoId(): Int {
        val sharedPreferences = getSharedPreferences("mi_app", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("alumnoId", -1)
    }
}
