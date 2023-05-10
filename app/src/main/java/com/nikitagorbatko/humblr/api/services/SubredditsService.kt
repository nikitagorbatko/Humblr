package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.PostResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SubredditsService {
    @GET("r/{displayName}")
    suspend fun getSubredditPosts(
        @Path("displayName") displayName: String,
        @Header("Authorization") accessToken: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
    ): PostResponseDto
}