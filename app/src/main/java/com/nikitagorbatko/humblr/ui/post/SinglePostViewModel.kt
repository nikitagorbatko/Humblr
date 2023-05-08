package com.nikitagorbatko.humblr.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.api.dto.CommentDto
import com.nikitagorbatko.humblr.api.dto.CommentsResponse
import com.nikitagorbatko.humblr.data.comments.CommentsRepository
import com.nikitagorbatko.humblr.data.posts.PostsRepository
import com.nikitagorbatko.humblr.ui.user.UserViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SinglePostViewModel(private val repository: CommentsRepository) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _comments = MutableStateFlow<List<CommentDto>?>(null)
    val comments = _comments.asStateFlow()

    suspend fun getComments(id: String) {
        try {
            _state.emit(State.LOADING)
            val comments = repository.getComments(id)
            _comments.emit(comments[1].data.children)
            _state.emit(State.PRESENT)
        } catch (_: Exception) {
            _state.emit(State.ERROR)
        }
    }

    enum class State { LOADING, PRESENT, ERROR }
}