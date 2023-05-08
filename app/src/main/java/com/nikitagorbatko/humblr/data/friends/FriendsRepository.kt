package com.nikitagorbatko.humblr.data.friends

import com.nikitagorbatko.humblr.api.dto.friends.FriendDTO

interface FriendsRepository {
    suspend fun getFriends(): List<FriendDTO>
}