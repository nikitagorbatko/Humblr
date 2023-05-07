package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.user.ChildUserDTO
import com.nikitagorbatko.humblr.api.dto.user.UserDTO

class GetAccountUseCase(private val service: RedditService, private val token: String) {
    suspend fun execute(): UserDTO {
        return service.getAboutMe(accessToken = token)
    }
}