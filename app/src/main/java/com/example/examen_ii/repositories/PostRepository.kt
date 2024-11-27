package com.example.examen_ii.repositories

import com.example.examen_ii.entities.PostEntity
import com.example.examen_ii.entities.UserEntity
import com.example.examen_ii.network.ClienteRetrofit
import com.example.examen_ii.services.PostService
import com.example.examen_ii.services.UserService

class PostRepository(private val postService: PostService = ClienteRetrofit.getInstanciaRetrofit2) {
    suspend fun getAllPosts(id: Long) : List<PostEntity> = postService.getAllPosts(id)
}