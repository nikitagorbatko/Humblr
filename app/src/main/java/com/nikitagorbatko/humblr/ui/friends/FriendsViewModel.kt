package com.nikitagorbatko.humblr.ui.friends

import androidx.lifecycle.ViewModel
import com.nikitagorbatko.humblr.api.pojos.FriendDto
import com.nikitagorbatko.humblr.data.friends.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FriendsViewModel(private val repository: FriendsRepository) : ViewModel() {
    private val _friends = MutableStateFlow<List<FriendDto>>(emptyList())
    val friends = _friends.asStateFlow()

    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    suspend fun getFriends() {
        try {
            val friends = repository.getFriends()
            _friends.emit(friends)
            _state.emit(State.PRESENT)
        } catch (_: Exception) {
            _state.emit(State.ERROR)
        }
    }

    enum class State { LOADING, PRESENT, ERROR }
}