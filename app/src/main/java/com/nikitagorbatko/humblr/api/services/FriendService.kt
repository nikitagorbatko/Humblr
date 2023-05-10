package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.FriendResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface FriendService {
    @GET("api/v1/me/friends")
    suspend fun getFriends(
        @Header("Authorization") accessToken: String
    ): FriendResponseDto
}