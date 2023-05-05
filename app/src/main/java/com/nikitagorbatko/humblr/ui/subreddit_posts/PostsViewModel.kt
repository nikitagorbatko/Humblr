package com.nikitagorbatko.humblr.ui.subreddit_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.data.posts.PostsRepository

class PostsViewModel(private val repository: PostsRepository) : ViewModel() {
    fun getPosts(displayName: String) = repository.getPosts(displayName).cachedIn(viewModelScope)
}