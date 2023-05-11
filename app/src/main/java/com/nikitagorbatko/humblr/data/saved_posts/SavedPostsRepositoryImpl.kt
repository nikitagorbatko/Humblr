package com.nikitagorbatko.humblr.data.saved_posts

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.App
import com.nikitagorbatko.humblr.api.pojos.ChildPostDto
import com.nikitagorbatko.humblr.api.services.MeService
import com.nikitagorbatko.humblr.api.services.SavedPostsService
import com.nikitagorbatko.humblr.data.name.NameRepository
import kotlinx.coroutines.flow.Flow
import org.koin.mp.KoinPlatform

class SavedPostsRepositoryImpl(
    private val token: String,
    private val service: SavedPostsService,
    private val nameRepository: NameRepository
) : SavedPostsRepository {

    override fun getSavedPosts(): Flow<PagingData<ChildPostDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = SavedPostsPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SavedPostsPagingSource(
                    token = token,
                    service = service,
                    nameRepository = nameRepository,
                )
            }
        ).flow
    }
}