package com.nikitagorbatko.humblr.data.friends

import com.nikitagorbatko.humblr.api.pojos.FriendDto

interface FriendsRepository {
    suspend fun getFriends(): List<FriendDto>
}