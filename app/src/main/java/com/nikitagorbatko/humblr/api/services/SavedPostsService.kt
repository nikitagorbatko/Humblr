package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.PostResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SavedPostsService {
    @GET("user/{username}/saved?&depth=1")
    suspend fun getSavedPosts(
        @Path("username") userName: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): PostResponseDto
}