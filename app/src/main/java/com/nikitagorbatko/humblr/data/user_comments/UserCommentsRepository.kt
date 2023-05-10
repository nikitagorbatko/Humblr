package com.nikitagorbatko.humblr.data.user_comments

import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import kotlinx.coroutines.flow.Flow

interface UserCommentsRepository {
    fun getComments(name: String): Flow<PagingData<CommentDto>>
}