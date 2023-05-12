package com.nikitagorbatko.humblr.data.friends_photos

import com.nikitagorbatko.humblr.api.pojos.UserDto
import com.nikitagorbatko.humblr.domain.GetUserUseCase

class FriendsPhotosRepositoryImpl(private val getUserUseCase: GetUserUseCase,): FriendsPhotosRepository {
    private val friendList: MutableList<UserDto> = mutableListOf()

    override suspend fun getFriendInfo(name: String): UserDto? {
        friendList.find { it.name == name }?.let { return it }
        return try {
            val friend = getUserUseCase.execute(author = name)?.data
            if (friend != null) {
                friendList.add(friend)
            }
            friend
        } catch (_: Exception) {
            null
        }
    }
}