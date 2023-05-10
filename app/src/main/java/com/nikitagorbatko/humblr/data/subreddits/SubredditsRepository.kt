package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import kotlinx.coroutines.flow.Flow

interface SubredditsRepository {
    fun getPopularSubreddits(): Flow<PagingData<ChildSubredditDto>>

    fun getNewSubreddits(): Flow<PagingData<ChildSubredditDto>>

    fun getQuerySubreddits(query: String): Flow<PagingData<ChildSubredditDto>>

    fun getDefaultSubreddits(): Flow<PagingData<ChildSubredditDto>>

    fun getFavouriteSubreddits(): Flow<PagingData<ChildSubredditDto>>
}