package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.services.SubscribeSubService

class SubscribeUseCase(private val service: SubscribeSubService, private val token: String) {
    suspend fun execute(subName: String) {
        try {
            service.subscribeToSub(subName = subName, token = token)
        } catch (_: Exception) {
        }
    }
}