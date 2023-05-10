package com.nikitagorbatko.humblr.data.comments

import com.nikitagorbatko.humblr.api.pojos.CommentsResponseDto
import com.nikitagorbatko.humblr.domain.GetAllCommentsUseCase

class CommentsRepositoryImpl(
    private val getAllCommentsUseCase: GetAllCommentsUseCase
): CommentsRepository {
    override suspend fun getComments(id: String): List<CommentsResponseDto> {
        return getAllCommentsUseCase.execute(id)
    }
}