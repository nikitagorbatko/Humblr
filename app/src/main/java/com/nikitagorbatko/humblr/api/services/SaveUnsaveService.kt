package com.nikitagorbatko.humblr.api.services

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SaveUnsaveService {
    @POST("api/save")
    suspend fun saveComment(
        @Query("id") id: String,
        @Header("Authorization") accessToken: String
    )

    @POST("api/unsave")
    suspend fun unsaveComment(
        @Query("id") id: String,
        @Header("Authorization") accessToken: String
    )
}