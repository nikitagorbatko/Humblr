package com.nikitagorbatko.humblr.data.saved_comments//package com.nikitagorbatko.humblr.data.comments


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.api.services.SavedCommentsService
import com.nikitagorbatko.humblr.data.name.NameRepository

class SavedCommentsPagingSource(
    private val token: String,
    private val nameRepository: NameRepository,
    private val service: SavedCommentsService,
) : PagingSource<String, CommentDto>() {
    private var myName: String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CommentDto> {
        val after = params.key ?: EMPTY_AFTER

        if (myName == null) {
            myName = nameRepository.fetchName()
        }

        return kotlin.runCatching {
            service.getSavedComments(
                userName = myName ?: return LoadResult.Error(Throwable("-_-")),
                accessToken = token,
                after = after,
            )
        }.fold(onSuccess = {
            LoadResult.Page(
                data = it.dataDto?.children ?: emptyList(),
                prevKey = it.dataDto?.before,
                nextKey = it.dataDto?.after
            )
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    override fun getRefreshKey(state: PagingState<String, CommentDto>) = EMPTY_AFTER

    companion object {
        private const val EMPTY_AFTER = ""
        const val PER_PAGE = 25
    }
}