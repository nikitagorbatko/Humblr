package com.nikitagorbatko.humblr.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.api.services.VoteUnvoteService
import com.nikitagorbatko.humblr.data.comments.CommentsRepository
import com.nikitagorbatko.humblr.domain.SaveUnsaveCommentUseCase
import com.nikitagorbatko.humblr.domain.VoteUnvoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SinglePostViewModel(
    private val repository: CommentsRepository,
    private val voteUnvoteUseCase: VoteUnvoteUseCase,
    private val saveUnsaveCommentUseCase: SaveUnsaveCommentUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _comments = MutableStateFlow<List<CommentUi>?>(null)
    val comments = _comments.asStateFlow()

    suspend fun getComments(id: String) {
        try {
            _state.emit(State.LOADING)
            val comments = repository.getComments(id)
            val commentsUi = CommentDtoToUiMapper.convert(comments?.get(1)?.dataDto?.children)
            _comments.emit(commentsUi)
            _state.emit(State.PRESENT)
        } catch (_: Exception) {
            _state.emit(State.ERROR)
        }
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