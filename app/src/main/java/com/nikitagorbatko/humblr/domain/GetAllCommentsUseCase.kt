package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.CommentsResponse

class GetAllCommentsUseCase(private val service: RedditService, private val token: String) {
    suspend fun execute(id: String): List<CommentsResponse> {
        return service.getPostComments(id = id, accessToken = token)
    }
}