package com.nikitagorbatko.humblr.data.saved_posts

import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.ChildPostDto
import kotlinx.coroutines.flow.Flow

interface SavedPostsRepository {
    fun getSavedPosts(): Flow<PagingData<ChildPostDto>>
}