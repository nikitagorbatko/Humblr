package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import com.nikitagorbatko.humblr.api.services.QuerySubredditsService

class SubredditsQueryPagingSource(
    private val token: String,
    private val service: QuerySubredditsService,
    private val query: String
) : PagingSource<String, ChildSubredditDto>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ChildSubredditDto> {
        val after = params.key ?: EMPTY_AFTER

        return kotlin.runCatching {
            service.getQuerySubreddits(accessToken = token, after = after, query = query).data
        }.fold(onSuccess = {
            LoadResult.Page(it.children, it.before, it.after)
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    override fun getRefreshKey(state: PagingState<String, ChildSubredditDto>) = EMPTY_AFTER

    companion object {
        private const val EMPTY_AFTER = ""
        const val PER_PAGE = 25
    }
}