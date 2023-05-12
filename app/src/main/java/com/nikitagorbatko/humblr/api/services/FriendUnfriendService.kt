package com.nikitagorbatko.humblr.api.services

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface FriendUnfriendService {
    @PUT("api/v1/me/friends/{username}")
    suspend fun addAsFriend(
        @Path("username") userName: String,
        @Body json: RequestBody,
        @Header("Authorization") accessToken: String
    ): Response<ResponseBody>

    @DELETE("api/v1/me/friends/{uname}")
    suspend fun removeFromFriends(
        @Path("uname") userName: String,
        @Query("id") id: String,
        @Header("Authorization") accessToken: String
    ): Response<ResponseBody>
}