package com.nikitagorbatko.humblr.ui.post

import androidx.lifecycle.ViewModel
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.data.comments.CommentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SinglePostViewModel(private val repository: CommentsRepository) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _comments = MutableStateFlow<List<CommentDto>?>(null)
    val comments = _comments.asStateFlow()

    suspend fun getComments(id: String) {
        //try {
            _state.emit(State.LOADING)
            val comments = repository.getComments(id)
            _comments.emit(comments[1].dataDto?.children)
            _state.emit(State.PRESENT)
        //} catch (_: Exception) {
            //_state.emit(State.ERROR)
        //}
    }

    enum class State { LOADING, PRESENT, ERROR }
}