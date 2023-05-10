package com.nikitagorbatko.humblr.data.friends

import com.nikitagorbatko.humblr.api.pojos.FriendDto
import com.nikitagorbatko.humblr.api.services.FriendService

class FriendsRepositoryImpl(
    private val token: String,
    private val service: FriendService
) : FriendsRepository {
    override suspend fun getFriends(): List<FriendDto> {
        return service.getFriends(token).data.children
    }
}