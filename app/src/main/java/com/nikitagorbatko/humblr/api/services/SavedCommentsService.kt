package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.CommentsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SavedCommentsService {
    @GET("/user/{username}/saved?type=comments")
    suspend fun getSavedComments(
        @Path("username") userName: String,
        @Query("before") before: String? = null,
        @Query("after") after: String? = null,
        @Header("Authorization") accessToken: String
    ): CommentsResponseDto
}