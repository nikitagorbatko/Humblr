package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO
import kotlinx.coroutines.flow.Flow

class SubredditsRepositoryImpl(private val token: String, private val service: RedditService) :
    SubredditsRepository {
    override fun getPopularSubreddits(): Flow<PagingData<ChildSubredditDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsPopularPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SubredditsPopularPagingSource(
                    token = token,
                    service = service
                )
            }
        ).flow
    }

    override fun getNewSubreddits(): Flow<PagingData<ChildSubredditDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsPopularPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SubredditsNewPagingSource(token = token, service = service) }
        ).flow
    }

    override fun getQuerySubreddits(query: String): Flow<PagingData<ChildSubredditDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsPopularPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SubredditsQueryPagingSource(
                    token = token,
                    service = service,
                    query = query
                )
            }
        ).flow
    }
}