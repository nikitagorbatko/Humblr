package com.nikitagorbatko.humblr.data.posts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.post.ChildPostDTO
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO

class PostsPagingSource(
    private val displayName: String,
    private val token: String,
    private val service: RedditService,
) : PagingSource<String, ChildPostDTO>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ChildPostDTO> {
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

    override fun getRefreshKey(state: PagingState<String, ChildPostDTO>) = EMPTY_AFTER

    companion object {
        private const val EMPTY_AFTER = ""
        const val PER_PAGE = 25
    }
}