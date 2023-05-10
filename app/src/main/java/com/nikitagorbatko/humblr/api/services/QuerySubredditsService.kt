package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.SubResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuerySubredditsService {
    @GET("/subreddits/search")
    suspend fun getQuerySubreddits(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("q") query: String,
        @Header("Authorization") accessToken: String
    ): SubResponseDto
}