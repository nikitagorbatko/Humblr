package com.nikitagorbatko.humblr.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.api.dto.CommentDto
import com.nikitagorbatko.humblr.api.dto.CommentsResponse
import com.nikitagorbatko.humblr.data.comments.CommentsRepository
import com.nikitagorbatko.humblr.data.posts.PostsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SinglePostViewModel(private val repository: CommentsRepository) : ViewModel() {
    private val _comments = MutableStateFlow<List<CommentDto>?>(null)
    val comments = _comments.asStateFlow()

    suspend fun getComments(id: String) {
        val comments = repository.getComments(id)
        _comments.emit(comments[1].data.children)
    }
}