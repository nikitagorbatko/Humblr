package com.nikitagorbatko.humblr.domain

import com.nikitagorbatko.humblr.api.services.FavouriteSubredditsService

//should be paginated
class GetFavouriteSubredditsUseCase(
    private val service: FavouriteSubredditsService,
    private val token: String
) {
    suspend fun execute() = service.getFavoriteSubreddits(accessToken = token).data.children

}