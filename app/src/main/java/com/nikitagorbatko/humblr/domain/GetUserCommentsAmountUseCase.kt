package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.services.UserCommentsService

class GetUserCommentsAmountUseCase(
    private val token: String,
    private val service: UserCommentsService,
) {
    suspend fun execute(username: String): Int {
        var total = 0
        var after: String? = null
        do {
            try {
                val comments = service.getUserComments(userName = username, accessToken = token)
                total += comments.dataDto?.children?.size ?: 0
                after = comments.dataDto?.after
            } catch (_: Exception) { }
        } while (after != null)
        return total
    }
}