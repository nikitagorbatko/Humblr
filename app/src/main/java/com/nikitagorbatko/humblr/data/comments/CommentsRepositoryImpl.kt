package com.nikitagorbatko.humblr.data.comments

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.CommentDto
import com.nikitagorbatko.humblr.api.dto.CommentsResponse
import com.nikitagorbatko.humblr.domain.GetAllCommentsUseCase
import kotlinx.coroutines.flow.Flow

class CommentsRepositoryImpl(
    private val token: String,
    //private val service: RedditService,
    private val getAllCommentsUseCase: GetAllCommentsUseCase
): CommentsRepository {
    override suspend fun getComments(id: String): List<CommentsResponse> {
        return getAllCommentsUseCase.execute(id)
//        return Pager(
//            config = PagingConfig(
//                pageSize = CommentsPagingSource.PER_PAGE,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {
//                CommentsPagingSource(
//                    id = id, token = token, service = service
//                )
//            }
//        ).flow
    }
}