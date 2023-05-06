package com.nikitagorbatko.humblr.data.comments

import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.dto.CommentDto
import com.nikitagorbatko.humblr.api.dto.CommentsResponse
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    suspend fun getComments(id: String): List<CommentsResponse>
}