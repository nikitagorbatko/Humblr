package com.nikitagorbatko.humblr.data.posts

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.ChildPostDto
import com.nikitagorbatko.humblr.api.services.SubredditsService
import com.nikitagorbatko.humblr.data.subreddits.SubredditsPopularPagingSource
import kotlinx.coroutines.flow.Flow

class PostsRepositoryImpl(
    private val token: String,
    private val service: SubredditsService
) : PostsRepository {
    override fun getPosts(displayName: String): Flow<PagingData<ChildPostDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = SubredditsPopularPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PostsPagingSource(
                    displayName = displayName, token = token, service = service
                )
            }
        ).flow
    }
}