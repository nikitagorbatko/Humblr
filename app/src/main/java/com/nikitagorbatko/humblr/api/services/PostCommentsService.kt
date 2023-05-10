package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.CommentsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PostCommentsService {
    @GET("comments/{id}")
    suspend fun getPostComments(
        @Path("id") id: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): List<CommentsResponseDto>
}