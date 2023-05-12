package com.nikitagorbatko.humblr.data.friends_photos

import com.nikitagorbatko.humblr.api.pojos.UserDto

interface FriendsPhotosRepository {
    suspend fun getFriendInfo(name: String): UserDto?
}