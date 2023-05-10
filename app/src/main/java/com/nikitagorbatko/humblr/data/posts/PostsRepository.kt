package com.nikitagorbatko.humblr.data.posts

import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.ChildPostDto
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(displayName: String): Flow<PagingData<ChildPostDto>>
}