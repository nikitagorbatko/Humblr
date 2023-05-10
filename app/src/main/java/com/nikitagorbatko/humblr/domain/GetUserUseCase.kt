package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.pojos.ChildUserDto
import com.nikitagorbatko.humblr.api.services.UserInfoService

class GetUserUseCase(private val service: UserInfoService, private val token: String) {
    suspend fun execute(author: String): ChildUserDto {
        return service.getUserInfo(userName = author, accessToken = token)
    }
}