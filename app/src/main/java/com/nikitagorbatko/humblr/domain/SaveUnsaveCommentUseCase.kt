package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.services.SaveUnsaveService

class SaveUnsaveCommentUseCase(
    private val service: SaveUnsaveService,
    private val token: String
) {
    suspend fun executeSave(id: String) {
        try {
            service.saveComment(id = id, accessToken = token)
        } catch (ex: Exception ) {
            ex.message
        }
    }

    suspend fun executeUnsave(id: String) {
        service.unsaveComment(id = id, accessToken = token)
    }
}