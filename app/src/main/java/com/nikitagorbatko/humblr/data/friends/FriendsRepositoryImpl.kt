package com.nikitagorbatko.humblr.data.friends

import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.friends.FriendDTO

class FriendsRepositoryImpl(
    private val token: String,
    private val service: RedditService
) : FriendsRepository {
    override suspend fun getFriends(): List<FriendDTO> {
        return service.getFriends(token).data.children
    }
}