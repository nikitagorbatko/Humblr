package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.RedditService
import com.squareup.moshi.Json
import okhttp3.MediaType
import okhttp3.RequestBody

class FriendUserUseCase(private val service: RedditService, private val token: String) {

    suspend fun execute(name: String) {
        try {
            val friendRequest = FriendRequest("ForRetrofit", "sample note")
            val body: RequestBody =
                RequestBody.create(MediaType.parse("application/json"), friendRequest.toString())

            val response = service.addAsFriend(
                userName = name,
                json = body,
                accessToken = token
            )
        } catch (ex: Exception) {
            ex.message
        }
    }

    class FriendRequest(
        @Json(name = "name") private val name: String,
        @Json(name = "note") private val note: String
    )
}