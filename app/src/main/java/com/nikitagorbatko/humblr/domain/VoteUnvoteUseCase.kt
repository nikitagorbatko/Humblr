package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.services.VoteUnvoteService

class VoteUnvoteUseCase(private val service: VoteUnvoteService, private val token: String) {
    suspend fun execute(id: String, dir: Int) {
        service.vote(id = id, dir = dir, accessToken = token)
    }
}