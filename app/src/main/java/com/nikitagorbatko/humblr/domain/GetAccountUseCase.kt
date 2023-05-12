package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.pojos.UserDto
import com.nikitagorbatko.humblr.api.services.MeService

class GetAccountUseCase(private val service: MeService, private val token: String) {
    suspend fun execute(): UserDto? {
       return try {
           service.getAboutMe(accessToken = token)
        } catch (_: Exception) {
            null
        }
    }
}