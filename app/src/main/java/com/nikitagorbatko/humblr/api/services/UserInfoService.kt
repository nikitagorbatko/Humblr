package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.ChildUserDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserInfoService {
    @GET("user/{name}/about")
    suspend fun getUserInfo(
        @Path("name") userName: String,
        @Header("Authorization") accessToken: String
    ): ChildUserDto
}