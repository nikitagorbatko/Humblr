package com.nikitagorbatko.humblr.data.subreddits

import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO
import com.nikitagorbatko.humblr.api.dto.subreddit.SubredditDTO
import kotlinx.coroutines.flow.Flow

interface SubredditsRepository {
    fun getPopularSubreddits(): Flow<PagingData<ChildSubredditDTO>>

    fun getNewSubreddits(): Flow<PagingData<ChildSubredditDTO>>

    fun getQuerySubreddits(query: String): Flow<PagingData<ChildSubredditDTO>>
}