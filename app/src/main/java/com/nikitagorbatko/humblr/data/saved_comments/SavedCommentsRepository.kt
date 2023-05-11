package com.nikitagorbatko.humblr.data.saved_comments

import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import kotlinx.coroutines.flow.Flow

interface SavedCommentsRepository {
    fun getComments(): Flow<PagingData<CommentDto>>
}