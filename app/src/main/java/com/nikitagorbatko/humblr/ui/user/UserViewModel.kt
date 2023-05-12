package com.nikitagorbatko.humblr.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.api.pojos.ChildUserDto
import com.nikitagorbatko.humblr.api.services.VoteUnvoteService
import com.nikitagorbatko.humblr.data.user_comments.UserCommentsRepository
import com.nikitagorbatko.humblr.domain.FriendUnfriendUserUseCase
import com.nikitagorbatko.humblr.domain.GetUserUseCase
import com.nikitagorbatko.humblr.domain.SaveUnsaveCommentUseCase
import com.nikitagorbatko.humblr.domain.VoteUnvoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val repository: UserCommentsRepository,
    private val friendUnfriendUserUseCase: FriendUnfriendUserUseCase,
    private val voteUnvoteUseCase: VoteUnvoteUseCase,
    private val saveUnsaveCommentUseCase: SaveUnsaveCommentUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _user = MutableStateFlow<ChildUserDto?>(null)
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
        friendUnfriendUserUseCase.executeAdd(name = name)
    }

    suspend fun removeFromFriends(name: String) {
        friendUnfriendUserUseCase.executeRemove(name = name)
    }

    //does not check the response
    fun voteUp(id: String) {
        viewModelScope.launch {
            voteUnvoteUseCase.execute(id = id, dir = VoteUnvoteService.voteUp)
        }
    }

    //does not check the response
    fun voteDown(id: String) {
        viewModelScope.launch {
            voteUnvoteUseCase.execute(id = id, dir = VoteUnvoteService.voteDown)
        }
    }

    //does not check the response
    fun saveComment(id: String) {
        viewModelScope.launch {
            saveUnsaveCommentUseCase.executeSave(id)
        }
    }

    //does not check the response
    fun unsaveComment(id: String) {
        viewModelScope.launch {
            saveUnsaveCommentUseCase.executeUnsave(id)
        }
    }

    enum class State { LOADING, PRESENT, ERROR }
}