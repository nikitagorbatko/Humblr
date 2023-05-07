package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.user.ChildUserDTO

class GetUserUseCase(private val service: RedditService, private val token: String) {
    suspend fun execute(author: String): ChildUserDTO {
        return service.getUserInfo(userName = author, accessToken = token)
    }
}