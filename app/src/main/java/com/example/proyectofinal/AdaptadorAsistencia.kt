package com.example.proyectofinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class AdaptadorAsistencia(val ListaElementos:ArrayList<Asistencia>): RecyclerView.Adapter<AdaptadorAsistencia.ViewHolder>() {

    override fun getItemCount(): Int {
        return ListaElementos.size;
    }
    class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val falumno = itemView.findViewById<TextView>(R.id.elemento_alumno)
        val ffecha = itemView.findViewById<TextView>(R.id.elemento_fecha)
        val festado = itemView.findViewById<TextView>(R.id.elemento_estado)
        val fnombre = itemView.findViewById<TextView>(R.id.elemento_alumno)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.falumno?.text=ListaElementos[position].alumno
        val fechaString = ListaElementos[position].fecha
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd")
        val fecha: Date? = try {
            formatoFecha.parse(fechaString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }

        val formato = SimpleDateFormat("yyyy/MM/dd")
        val fechaFormateada = fecha?.let { formato.format(it) }

        holder?.ffecha?.text = fechaFormateada
        holder?.festado?.text=ListaElementos[position].estado


        var estado = ListaElementos[position].estado
        var id = ListaElementos[position].id

        holder.itemView.setOnClickListener(){
            var llamaractividad = Intent(holder.itemView.context, actualizarAsistencia::class.java)
            llamaractividad.putExtra("id", id.toString())
            llamaractividad.putExtra("estado", estado.toString())
            holder.itemView.context.startActivity(llamaractividad)
        }





    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementosasistencia, parent, false);
        return ViewHolder(v);
    }
}