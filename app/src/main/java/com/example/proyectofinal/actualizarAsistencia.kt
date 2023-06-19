package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_actualizar_asistencia.btnActualizar
import kotlinx.android.synthetic.main.activity_actualizar_asistencia.cmbCategorias

class actualizarAsistencia : AppCompatActivity() {
    private lateinit var cmbCategorias: Spinner
    private lateinit var btnActualizar: Button
    private var asistenciaId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_asistencia)
        cmbCategorias = findViewById(R.id.cmbCategorias)
        btnActualizar = findViewById(R.id.btnActualizar)

        val estado = arrayOf("Asistió", "Tardanza","Faltante")
        cmbCategorias.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, estado
            )
        )
        btnActualizar.setOnClickListener(){
            actAsistencia()
        }
        val bundle: Bundle? = intent.extras
        if (bundle != null){
            asistenciaId = bundle.getInt("id")
            when (bundle.getString("estado").toString()){
                "A" -> cmbCategorias.setSelection(0)
                "T" -> cmbCategorias.setSelection(1)
                "F" -> cmbCategorias.setSelection(2)
            }
        }
    }
    fun actAsistencia() {
        val clave = when (cmbCategorias.selectedItem.toString()) {
            "Asistió" -> "A"
            "Tardanza" -> "T"
            "Faltante" -> "F"
            else -> ""
        }

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.urlAPI) + "/api/admin/asistencia/$asistenciaId"

        val putRequest = object : StringRequest(
            Request.Method.PUT, url,
            Response.Listener { response ->
                Toast.makeText(
                    applicationContext,
                    "usuario actualizado correctamente",
                    Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    "Error al actualizar el usuario",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["clave"] = clave
                return params
            }
        }

        queue.add(putRequest)
    }

}