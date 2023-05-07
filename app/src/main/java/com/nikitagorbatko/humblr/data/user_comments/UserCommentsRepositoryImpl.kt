package com.nikitagorbatko.humblr.data.user_comments

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.CommentDto
import kotlinx.coroutines.flow.Flow

class UserCommentsRepositoryImpl(
    private val token: String,
    private val service: RedditService,
): UserCommentsRepository {
    override fun getComments(name: String): Flow<PagingData<CommentDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = UserCommentsPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserCommentsPagingSource(
                    name = name, token = token, service = service
                )
            }
        ).flow
    }
}