package com.nikitagorbatko.humblr.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.api.dto.user.ChildUserDTO
import com.nikitagorbatko.humblr.data.user_comments.UserCommentsRepository
import com.nikitagorbatko.humblr.domain.FriendUserUseCase
import com.nikitagorbatko.humblr.domain.GetUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow

class UserViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val repository: UserCommentsRepository,
    private val friendUserUseCase: FriendUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _user = MutableStateFlow<ChildUserDTO?>(null)
    val user = _user.asStateFlow()

    suspend fun getUserInfo(author: String) {
        try {
            val user = getUserUseCase.execute(author = author)
            _user.emit(user)
            _state.emit(State.PRESENT)
        } catch (_: Exception) {
            _state.emit(State.ERROR)
        }
    }

    fun getUserComments(name: String) =
        repository.getComments(name).cachedIn(viewModelScope)

    suspend fun addAsFriend(name: String) {
        friendUserUseCase.execute(name = name)
    }

    enum class State { LOADING, PRESENT, ERROR }
}