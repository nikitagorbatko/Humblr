package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.pojos.CommentsResponseDto
import com.nikitagorbatko.humblr.api.services.PostCommentsService

class GetAllCommentsUseCase(private val service: PostCommentsService, private val token: String) {
    suspend fun execute(id: String): List<CommentsResponseDto>? {
        return try {
            service.getPostComments(id = id, accessToken = token)
        } catch (_: Exception) {
            null
        }
    }
}