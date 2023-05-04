package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO
import kotlinx.coroutines.flow.Flow

class SubredditsRepositoryImpl(private val token: String, private val service: RedditService) :
    SubredditsRepository {
    override fun getPopularSubreddits(token: String): Flow<PagingData<ChildSubredditDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = {
                SubredditsPagingSource(
                    token = token, service = service
                )
            }
        ).flow
    }
}