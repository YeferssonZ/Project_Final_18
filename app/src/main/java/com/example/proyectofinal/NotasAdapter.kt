package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotasAdapter : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {
    private val notas = ArrayList<AppNota>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_notas, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.bind(nota)
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    fun setNotas(notas: List<AppNota>) {
        this.notas.clear()
        this.notas.addAll(notas)
        notifyDataSetChanged()
    }

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(nota: AppNota) {
            // Configurar los valores de los elementos de vista en el ViewHolder
            val txtNotaId: TextView = itemView.findViewById(R.id.txtNotaId)
            val txtValorNota: TextView = itemView.findViewById(R.id.txtValorNota)
            val txtLaboratorio: TextView = itemView.findViewById(R.id.txtLaboratorio)

            txtNotaId.text = "ID de la nota: ${nota.id}"
            txtValorNota.text = "Valor de la nota: ${nota.valorNota}"
            txtLaboratorio.text = "Laboratorio: ${nota.laboratorio}"
        }
    }
}
