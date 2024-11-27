package com.example.examen_ii.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examen_ii.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempDB = INSTANCE
            if (tempDB != null) {
                return tempDB
            }
            // Solo se tenga un accceso al mismo tiempo en procesos concurrentes o en solicitudes
            // concurrentes
            // Solo el metodo 1 tenga la facultad de crearla
            synchronized(this) {
                // Generamos la instancia de la base de datos
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}