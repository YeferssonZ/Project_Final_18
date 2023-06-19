package com.example.proyectofinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CursoAdapter(private val alumnoId: Int) : RecyclerView.Adapter<CursoAdapter.CursoViewHolder>() {

    private val cursos = ArrayList<AppCurso>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_cursos, parent, false)
        val viewHolder = CursoViewHolder(view)

        // Configurar el clic en el elemento de vista
        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            val curso = cursos[position]

            // Crear un Intent para abrir la actividad DetallesCursoActivity
            val intent = Intent(view.context, DetallesCursoActivity::class.java)
            intent.putExtra("cursoId", curso.id)
            view.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursos[position]
        holder.bind(curso)
    }

    override fun getItemCount(): Int {
        return cursos.size
    }

    fun setCursos(cursos: List<AppCurso>) {
        this.cursos.clear()
        this.cursos.addAll(cursos)
        notifyDataSetChanged()
    }

    fun actualizarNombreAlumno(alumnoId: Int, nombreAlumno: String) {
        cursos.forEach { curso ->
            if (curso.alumno == alumnoId) {
                curso.nombreAlumno = nombreAlumno
            }
        }
        notifyDataSetChanged()
    }

    fun actualizarNombreCurso(cursoId: Int, nombreCurso: String) {
        val curso = cursos.find { it.curso == cursoId }
        curso?.nombreCurso = nombreCurso
        curso?.let {
            val position = cursos.indexOf(it)
            notifyItemChanged(position)
        }
    }

    class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(curso: AppCurso) {
            val txtCursoId: TextView = itemView.findViewById(R.id.txtCursoId)
            val txtAlumnoId: TextView = itemView.findViewById(R.id.txtAlumnoId)
            val txtCursoNombre: TextView = itemView.findViewById(R.id.txtCursoNombre)

            txtCursoId.text = "ID del curso: ${curso.id}"
            txtAlumnoId.text = "Alumno: ${curso.alumno}"
            txtCursoNombre.text = "Curso: ${curso.curso}"

            // Verificar si se ha actualizado el nombre del alumno y mostrarlo si está disponible
            if (!curso.nombreAlumno.isNullOrEmpty()) {
                txtAlumnoId.text = "Alumno: ${curso.nombreAlumno}"
            } else {
                txtAlumnoId.text = "Alumno: ${curso.alumno}"
            }


            // Verificar si se ha actualizado el nombre del curso y mostrarlo si está disponible
            if (!curso.nombreCurso.isNullOrEmpty()) {
                txtCursoNombre.text = "Curso: ${curso.nombreCurso}"
            }
        }
    }
}
