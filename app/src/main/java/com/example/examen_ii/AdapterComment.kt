package com.example.examen_ii

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ii.entities.CommentEntity

class AdapterComment(var listadoComments: List<CommentEntity>):
    RecyclerView.Adapter<AdapterComment.ViewHolder>() {

    /**
     * Toodo es interno al adaptador
     */

    // Despues inicializamos la variable
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        // Despues implementamos lo que hacen los metodos
        fun onItemClick(comment: CommentEntity)
        fun onItemLongClick(comment: CommentEntity)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    // Internamente requerimos una clase
    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        // Referencia a los elementos de mi diseño

        val nameComment : TextView = itemView.findViewById(R.id.tvNameRecyclerView)
        var emailComment: TextView = itemView.findViewById(R.id.tvEmailRecyclerView)
        val bodyComment : TextView = itemView.findViewById(R.id.tvBodyRecyclerView)

        // Confirmar que se crearon las vistas
        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoComments[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoComments[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crear la lista -  Inflate
        val vista: View =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_custom, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind - Unir los datos con la vista
        val comment = listadoComments[position]

        holder.nameComment.text = comment.name
        holder.emailComment.text = comment.email
        holder.bodyComment.text = comment.body
    }

    override fun getItemCount() = listadoComments.size


    fun updateCommentList(newCommentList: List<CommentEntity>) {
        newCommentList.forEach {
            Log.d("Comment", "Adaptador Comment: ${it}.")
        }
        listadoComments = newCommentList
        notifyDataSetChanged()
    }
}