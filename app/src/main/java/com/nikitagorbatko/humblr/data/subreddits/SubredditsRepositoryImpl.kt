package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import com.nikitagorbatko.humblr.api.services.*
import kotlinx.coroutines.flow.Flow

class SubredditsRepositoryImpl(
    private val token: String,
    private val popularSubredditsService: PopularSubredditsService,
    private val newSubredditsService: NewSubredditsService,
    private val querySubredditsService: QuerySubredditsService,
    private val defaultSubredditsService: DefaultSubredditsService,
    private val favouriteSubredditsService: FavouriteSubredditsService
) :
    SubredditsRepository {
    override fun getPopularSubreddits(): Flow<PagingData<ChildSubredditDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsPopularPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SubredditsPopularPagingSource(
                    token = token,
                    service = popularSubredditsService
                )
            }
        ).flow
    }

    override fun getNewSubreddits(): Flow<PagingData<ChildSubredditDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsNewPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SubredditsNewPagingSource(
                    token = token,
                    service = newSubredditsService
                )
            }
        ).flow
    }

    override fun getQuerySubreddits(query: String): Flow<PagingData<ChildSubredditDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsQueryPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SubredditsQueryPagingSource(
                    token = token,
                    service = querySubredditsService,
                    query = query
                )
            }
        ).flow
    }

    override fun getDefaultSubreddits(): Flow<PagingData<ChildSubredditDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsQueryPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SubredditsDefaultPagingSource(
                    token = token,
                    service = defaultSubredditsService
                )
            }
        ).flow
    }

    override fun getFavouriteSubreddits(): Flow<PagingData<ChildSubredditDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsQueryPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SubredditsSavedPagingSource(
                    token = token,
                    service = favouriteSubredditsService
                )
            }
        ).flow
    }
}