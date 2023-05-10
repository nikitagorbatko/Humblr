package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.pojos.UserDto
import com.nikitagorbatko.humblr.api.services.MeService

class GetAccountUseCase(private val service: MeService, private val token: String) {
    suspend fun execute(): UserDto {
        return service.getAboutMe(accessToken = token)
    }
}