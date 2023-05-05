package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.RedditService

class SubscribeUseCase(private val service: RedditService, private val token: String) {
    suspend fun execute(subName: String) {
        service.subscribeToSub(subName = subName, token = token)
    }
}