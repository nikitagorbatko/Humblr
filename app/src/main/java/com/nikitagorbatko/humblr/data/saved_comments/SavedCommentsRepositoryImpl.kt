package com.nikitagorbatko.humblr.data.saved_comments

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.api.services.SavedCommentsService
import com.nikitagorbatko.humblr.data.name.NameRepository
import kotlinx.coroutines.flow.Flow

class SavedCommentsRepositoryImpl(
    private val token: String,
    private val nameRepository: NameRepository,
    private val service: SavedCommentsService
) : SavedCommentsRepository {
    override fun getComments(): Flow<PagingData<CommentDto>> {
        return Pager(
            config = PagingConfig(SavedCommentsPagingSource.PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = {
                SavedCommentsPagingSource(
                    token = token, nameRepository = nameRepository, service = service
                )
            }
        ).flow
    }
}