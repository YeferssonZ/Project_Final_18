package com.example.proyectofinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorElementos(val ListaElementos:ArrayList<Elementos>): RecyclerView.Adapter<AdaptadorElementos.ViewHolder>() {

    override fun getItemCount(): Int {
        return ListaElementos.size;
    }
    class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val fNombre = itemView.findViewById<TextView>(R.id.elemento_nombre)
        val fCiclo = itemView.findViewById<TextView>(R.id.elemento_ciclo)

        //set the onclick listener for the singlt list item
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.fNombre?.text=ListaElementos[position].nombre
        holder?.fCiclo?.text= ListaElementos[position].ciclo.toString()
        var id = ListaElementos[position].id

        holder.itemView.setOnClickListener(){
            var llamaractividad = Intent(holder.itemView.context, verAsistenciaProfesor::class.java)
            llamaractividad.putExtra("id", id.toString())
            holder.itemView.context.startActivity(llamaractividad)
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslista, parent, false);
        return ViewHolder(v);
    }
}