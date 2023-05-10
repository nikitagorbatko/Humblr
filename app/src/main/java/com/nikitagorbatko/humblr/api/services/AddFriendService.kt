package com.nikitagorbatko.humblr.api.services

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddFriendService {
    @PUT("api/v1/me/friends/{uname}")
    suspend fun addAsFriend(
        @Path("uname") userName: String,
        @Body json: RequestBody,
        @Header("Authorization") accessToken: String
    ): Response<ResponseBody>
}