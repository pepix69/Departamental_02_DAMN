package com.example.examen_ii

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ii.entities.UserEntity


class CustomAdapterRecyclerView(var listadoUsers: List<UserEntity>) :
    RecyclerView.Adapter<CustomAdapterRecyclerView.ViewHolder>() {

    /**
     * Toodo es interno al adaptador
     */

    // Despues inicializamos la variable
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        // Despues implementamos lo que hacen los metodos
        fun onItemClick(user: UserEntity)
        fun onItemLongClick(user: UserEntity)
    }

    fun setOnItemClickListener(listener: CustomAdapterRecyclerView.onItemClickListener) {
        miListener = listener
    }

    // Internamente requerimos una clase
    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        // Referencia a los elementos de mi diseño
        var nameUser: TextView = itemView.findViewById(R.id.tvNameRecyclerView)
        val userNameUser: TextView = itemView.findViewById(R.id.tvUserNameRecyclerView)
        val emailUser: TextView = itemView.findViewById(R.id.tvemailRecyclerView)

        // Confirmar que se crearon las vistas
        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoUsers[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoUsers[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crear la lista -  Inflate
        val vista: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_item_recycler_view, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun getItemCount() = listadoUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind - Unir los datos con la vista
        val user = listadoUsers[position]
        holder.nameUser.text = user.name
        holder.userNameUser.text = user.username
        holder.emailUser.text = user.email

    }

    fun updateList(newUsersList: List<UserEntity>) {
        newUsersList.forEach {
            Log.d("Cat", "Adaptador User: ${it}.")
        }
        listadoUsers = newUsersList
        notifyDataSetChanged()
    }
}