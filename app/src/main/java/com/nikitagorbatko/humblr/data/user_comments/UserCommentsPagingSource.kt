package com.nikitagorbatko.humblr.data.user_comments//package com.nikitagorbatko.humblr.data.comments


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.api.services.UserCommentsService

class UserCommentsPagingSource(
    private val name: String,
    private val token: String,
    private val service: UserCommentsService,
) : PagingSource<String, CommentDto>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CommentDto> {
        val after = params.key ?: EMPTY_AFTER

        return kotlin.runCatching {
            service.getUserComments(
                userName = name,
                accessToken = token,
                after = after,
            ).dataDto
        }.fold(onSuccess = {
            LoadResult.Page(it?.children!!, it.before, it.after)
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    override fun getRefreshKey(state: PagingState<String, CommentDto>) = EMPTY_AFTER

    companion object {
        private const val EMPTY_AFTER = ""
        const val PER_PAGE = 25
    }
}