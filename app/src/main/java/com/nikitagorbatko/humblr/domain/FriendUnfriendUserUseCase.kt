package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.services.FriendUnfriendService
import okhttp3.MediaType
import okhttp3.RequestBody


class FriendUnfriendUserUseCase(private val service: FriendUnfriendService, private val token: String) {
    suspend fun executeAdd(name: String) {
        try {
            val json = "{\"id\":\"$name\"}"
            val body: RequestBody =
                RequestBody.create(MediaType.parse("application/json"), json)

            service.addAsFriend(
                userName = name,
                json = body,
                accessToken = token
            )
        } catch (ex: Exception) {
            ex.message
        }
    }

    suspend fun executeRemove(name: String) {
        try {
            service.removeFromFriends(
                userName = name,
                id = name,
                accessToken = token
            )
        } catch (ex: Exception) {
            ex.message
        }
    }
}