package com.nikitagorbatko.humblr.ui.subreddit_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.data.posts.PostsRepository
import com.nikitagorbatko.humblr.domain.SubscribeUseCase
import com.nikitagorbatko.humblr.domain.UnsubscribeUseCase

class PostsViewModel(
    private val repository: PostsRepository,
    private val subscribeUseCase: SubscribeUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase
) : ViewModel() {
    fun getPosts(displayName: String) = repository.getPosts(displayName).cachedIn(viewModelScope)

    suspend fun subscribeToSub(subname: String) {
        subscribeUseCase.execute(subname)
    }

    suspend fun unsubscribeFromSub(subname: String) {
        unsubscribeUseCase.execute(subname)
    }
}