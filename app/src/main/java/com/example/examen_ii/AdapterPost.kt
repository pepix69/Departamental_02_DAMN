package com.example.examen_ii

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ii.entities.PostEntity

class AdapterPost(var listadoPosts: List<PostEntity>):
    RecyclerView.Adapter<AdapterPost.ViewHolder>() {

    /**
     * Toodo es interno al adaptador
     */

    // Despues inicializamos la variable
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        // Despues implementamos lo que hacen los metodos
        fun onItemClick(post: PostEntity)
        fun onItemLongClick(post: PostEntity)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    // Internamente requerimos una clase
    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        // Referencia a los elementos de mi diseño

        val titlePost : TextView = itemView.findViewById(R.id.tvTitleRecyclerView)
        var bodyPost: TextView = itemView.findViewById(R.id.tvBodyRecyclerView)

        // Confirmar que se crearon las vistas
        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoPosts[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoPosts[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crear la lista -  Inflate
        val vista: View =
            LayoutInflater.from(parent.context).inflate(R.layout.posts_custom, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind - Unir los datos con la vista
        val post = listadoPosts[position]
        holder.titlePost.text = post.title
        holder.bodyPost.text = post.body
    }

    override fun getItemCount() = listadoPosts.size


    fun updatePostList(newPostList: List<PostEntity>) {
        newPostList.forEach {
            Log.d("Cat", "Adaptador Post: ${it}.")
        }
        listadoPosts = newPostList
        notifyDataSetChanged()
    }
}