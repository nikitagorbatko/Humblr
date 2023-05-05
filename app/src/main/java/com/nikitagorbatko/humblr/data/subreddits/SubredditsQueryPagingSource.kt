package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO

class SubredditsQueryPagingSource(
    private val token: String,
    private val service: RedditService,
    private val query: String
) : PagingSource<String, ChildSubredditDTO>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ChildSubredditDTO> {
        val after = params.key ?: EMPTY_AFTER

        return kotlin.runCatching {
            service.getQuerySubreddits(accessToken = token, after = after, query = query).data
        }.fold(onSuccess = {
            LoadResult.Page(it.children, it.before, it.after)
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    override fun getRefreshKey(state: PagingState<String, ChildSubredditDTO>) = EMPTY_AFTER

    companion object {
        private const val EMPTY_AFTER = ""
        const val PER_PAGE = 25
    }
}