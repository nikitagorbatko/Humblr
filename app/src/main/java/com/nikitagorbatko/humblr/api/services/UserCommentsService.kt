package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.CommentsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UserCommentsService {
    @GET("/user/{username}/comments")
    suspend fun getUserComments(
        @Path("username") userName: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): CommentsResponseDto
}