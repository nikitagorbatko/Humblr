package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.services.UnsubscribeSubService

class UnsubscribeUseCase(private val service: UnsubscribeSubService, private val token: String) {
    suspend fun execute(subName: String) {
        try {
            service.unsubscribeFromSub(subName = subName, token = token)
        } catch (_: Exception) {
        }
    }
}