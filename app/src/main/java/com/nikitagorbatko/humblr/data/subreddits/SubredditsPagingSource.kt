package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO

class SubredditsPagingSource(
    private val token: String,
    private val service: RedditService,
) : PagingSource<Int, ChildSubredditDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChildSubredditDTO> {
        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            service.getPopularSubreddits(accessToken = token).data.children
        }.fold(onSuccess = {
            LoadResult.Page(
                it, null, page + 1
            )
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    override fun getRefreshKey(state: PagingState<Int, ChildSubredditDTO>) = FIRST_PAGE

    companion object {
        private const val FIRST_PAGE = 1
        const val PER_PAGE = 30
    }
}