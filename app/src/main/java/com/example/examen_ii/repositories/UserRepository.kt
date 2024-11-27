package com.example.examen_ii.repositories

import com.example.examen_ii.entities.UserEntity
import com.example.examen_ii.network.ClienteRetrofit
import com.example.examen_ii.services.UserService

class UserRepository(private val userService: UserService = ClienteRetrofit.getInstanciaRetrofit) {

    suspend fun getAllUsers() : List<UserEntity> = userService.getAllUsers()

}