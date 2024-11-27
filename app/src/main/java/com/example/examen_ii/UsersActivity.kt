package com.example.examen_ii

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ii.data.UserDatabase
import com.example.examen_ii.entities.UserEntity
import com.example.examen_ii.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersActivity : AppCompatActivity() {

    private val userRepository = UserRepository()

    private var escucharDatosLocales = false

    companion object {
        var adaptadorRecyler: CustomAdapterRecyclerView = CustomAdapterRecyclerView(emptyList())
    }

    // lateinit var adaptadorRecyler: CustomAdapterRecyclerView
    lateinit var recyclerViewUsers: RecyclerView

    lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = UserDatabase.getDatabase(this)


        // adaptadorRecyler = CustomAdapterRecyclerView(emptyList())
        recyclerViewUsers = findViewById(R.id.rvUsers)
        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        // Le pasamos el adaptador al RecyclerView
        recyclerViewUsers.adapter = adaptadorRecyler


        obtenerUsers()

        adaptadorRecyler.setOnItemClickListener(object :
            CustomAdapterRecyclerView.onItemClickListener {
            override fun onItemClick(user: UserEntity) {
                Log.d("Users", "Clic corto, Users ${user.name}}")
                val intent = Intent(this@UsersActivity, PostsActivity::class.java)
                intent.putExtra("id_user", user.id) // Envías una clave y un valor.
                Toast.makeText(this@UsersActivity, user.id.toString(), Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }

            override fun onItemLongClick(user: UserEntity) {
                Log.d("Users", "Clic largo: Users ${user.name}")
                guardarUser(user)
            }

        })

    }

    private fun obtenerUsers() {
        if (hayConexion()) {
            escucharDatosLocales = false // No escuchar datos locales cuando hay internet
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val users = userRepository.getAllUsers()
                    withContext(Dispatchers.Main) {
                        adaptadorRecyler.updateList(users)
                        Toast.makeText(this@UsersActivity, "Mostrando datos de la API", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    manejarError(e)
                }
            }
        } else {
            escucharDatosLocales = true // Escuchar datos locales si no hay internet
            CoroutineScope(Dispatchers.IO).launch {
                db.UserDao().getAllUsers().collect { usuariosLocales ->
                    if (escucharDatosLocales) { // Solo actualiza si debes mostrar datos locales
                        withContext(Dispatchers.Main) {
                            adaptadorRecyler.updateList(usuariosLocales)
                            Toast.makeText(this@UsersActivity, "Mostrando datos locales", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun guardarUser(user: UserEntity) {
        //Log.d("Pruebas", "Valor raza en guardarMascota ${raza}")
        // Configurar el Spinner
        val idUser = user.id
        val nameUser = user.name
        val userNameUser = user.username
        val emailUser = user.email

        try {
            //Log.d("Pruebas", "Raza selected: $imgUri")
            GlobalScope.launch {
                db.UserDao().add(
                    UserEntity(
                        id = idUser,
                        name = nameUser,
                        username = userNameUser,
                        email = emailUser
                    )
                )
            }
            Toast.makeText(this@UsersActivity, "Usuario guardado en la base de datos", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this@UsersActivity, "Error $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hayConexion(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private suspend fun manejarError(e: Exception) {
        Log.d("Users", "Error al obtener users ${e.message}")
        if (e is retrofit2.HttpException && e.code() == 404) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@UsersActivity,
                    "Aún no hay Users",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@UsersActivity,
                    "Error al obtener datos: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}