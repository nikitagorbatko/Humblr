package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.SubResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FavouriteSubredditsService {
    @GET("subreddits/mine/subscriber")
    suspend fun getFavoriteSubreddits(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): SubResponseDto
}