package com.example.examen_ii.repositories

import com.example.examen_ii.entities.CommentEntity
import com.example.examen_ii.entities.PostEntity
import com.example.examen_ii.network.ClienteRetrofit
import com.example.examen_ii.services.CommentService
import com.example.examen_ii.services.PostService

class CommentRepository(private val commentService: CommentService = ClienteRetrofit.getInstanciaRetrofit3) {
    suspend fun getAllComments(id: Long) : List<CommentEntity> = commentService.getAllComments(id)
}