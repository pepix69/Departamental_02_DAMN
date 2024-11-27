package com.example.examen_ii.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.examen_ii.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * from user WHERE id = :id")
    fun getById(id: Int): Flow<UserEntity>

    @Insert
    suspend fun add(user: UserEntity): Long

}