package com.nikitagorbatko.humblr.data.posts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.api.pojos.ChildPostDto
import com.nikitagorbatko.humblr.api.services.SubredditsService

class PostsPagingSource(
    private val displayName: String,
    private val token: String,
    private val service: SubredditsService,
) : PagingSource<String, ChildPostDto>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ChildPostDto> {
        val after = params.key ?: EMPTY_AFTER

        return kotlin.runCatching {
            service.getSubredditPosts(
                displayName = displayName,
                accessToken = token,
                after = after
            ).data
        }.fold(onSuccess = {
            LoadResult.Page(it.children, it.before, it.after)
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    override fun getRefreshKey(state: PagingState<String, ChildPostDto>) = EMPTY_AFTER

    companion object {
        private const val EMPTY_AFTER = ""
        const val PER_PAGE = 25
    }
}