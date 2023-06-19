package com.example.proyectofinal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class customAdapter: RecyclerView.Adapter<customAdapter.ViewHolder>() {
    var curso = arrayOf("")
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.elementoslista, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCurso.text = curso[i]
    }

    override fun getItemCount(): Int {
        return curso.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemCurso : TextView
        init{
            itemCurso = itemView.findViewById(R.id.elemento_nombre)
        }
    }



}