package com.nikitagorbatko.humblr.data.comments

import com.nikitagorbatko.humblr.api.pojos.CommentsResponseDto

interface CommentsRepository {
    suspend fun getComments(id: String): List<CommentsResponseDto>?
}