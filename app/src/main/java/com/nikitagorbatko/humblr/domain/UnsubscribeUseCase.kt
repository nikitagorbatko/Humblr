package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.RedditService

class UnsubscribeUseCase(private val service: RedditService, private val token: String) {
    suspend fun execute(subName: String) {
        service.unsubscribeFromSub(subName = subName, token = token)
    }
}