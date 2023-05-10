package com.nikitagorbatko.humblr.api.services

import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SubscribeSubService {
    @POST("api/subscribe?action=sub")
    suspend fun subscribeToSub(
        @Query("sr") subName: String,
        @Header("Authorization") token: String
    )
}