package com.example.examen_ii.services

import com.example.examen_ii.entities.CommentEntity
import com.example.examen_ii.entities.PostEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentService {
    @GET("posts/{id}/comments")
    suspend fun getAllComments(@Path("id") id: Long): List<CommentEntity>
}