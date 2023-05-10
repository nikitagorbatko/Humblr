package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface MeService {
    @GET("/api/v1/me")
    suspend fun getAboutMe(
        @Header("Authorization") accessToken: String
    ): UserDto
}