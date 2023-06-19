package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_logueo.*
import org.json.JSONArray

class LogueoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logueo)

        txtClaveLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        txtClaveLayout.isEndIconVisible = true

        txtClave.transformationMethod = PasswordTransformationMethod.getInstance()

        txtClaveLayout.setEndIconOnClickListener {
            val currentTransformationMethod = txtClave.transformationMethod
            if (currentTransformationMethod is PasswordTransformationMethod) {
                txtClave.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                txtClave.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        btnLoguear.setOnClickListener {
            val usuario = txtActualizar.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            val urlAPI = getString(R.string.urlAPI)

            val url = "$urlAPI/api/admin/cuenta"
            val request = object : StringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->
                    val jsonArray = JSONArray(response)

                    // Verificar las credenciales de inicio de sesión
                    var loggedIn = false
                    var rolUsuario = 0
                    var cuentaId = -1
                    for (i in 0 until jsonArray.length()) {
                        val cuenta = jsonArray.getJSONObject(i)
                        val username = cuenta.getString("username")
                        val password = cuenta.getString("password")
                        val rol = cuenta.getInt("rol")
                        if (usuario == username && clave == password) {
                            loggedIn = true
                            rolUsuario = rol
                            cuentaId = cuenta.getInt("id")
                            AppData.appId = cuentaId
                            break
                        }
                    }

                    // Mostrar el mensaje según el resultado del inicio de sesión
                    if (loggedIn) {
                        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                        // Redirigir a la actividad correspondiente según el rol del usuario
                        when (rolUsuario) {
                            1 -> {
                                val intent = Intent(this, AlumnoActivity::class.java).apply {
                                    putExtra("cuentaId", cuentaId)
                                }
                                startActivity(intent)
                                finish()
                            }
                            2 -> {
                                val intent = Intent(this, ProfesorActivity::class.java).apply {
                                    putExtra("cuentaId", cuentaId)
                                }
                                startActivity(intent)
                                finish()
                            }
                            else -> {
                                // Manejar caso de rol desconocido o error
                            }
                        }
                    } else {
                        Toast.makeText(this, "Error en usuario o contraseña", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error en la conexión: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            ) {}

            // Agregar la solicitud a la cola de solicitudes de Volley
            queue.add(request)
        }
    }
}
