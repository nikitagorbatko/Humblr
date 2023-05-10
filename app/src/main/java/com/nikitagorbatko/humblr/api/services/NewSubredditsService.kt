package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.SubResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewSubredditsService {
    @GET("subreddits/new")
    suspend fun getNewSubreddits(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("count") count: Int = 0,
        @Header("Authorization") accessToken: String
    ): SubResponseDto
}