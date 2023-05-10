package com.nikitagorbatko.humblr.api.services

import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UnsubscribeSubService {
    @POST("api/subscribe?action=unsub")
    suspend fun unsubscribeFromSub(
        @Query("sr") subName: String,
        @Header("Authorization") token: String
    )
}