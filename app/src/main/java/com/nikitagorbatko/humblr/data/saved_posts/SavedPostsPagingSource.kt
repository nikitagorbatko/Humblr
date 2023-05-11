package com.nikitagorbatko.humblr.data.saved_posts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nikitagorbatko.humblr.App
import com.nikitagorbatko.humblr.api.pojos.ChildPostDto
import com.nikitagorbatko.humblr.api.services.MeService
import com.nikitagorbatko.humblr.api.services.SavedPostsService
import com.nikitagorbatko.humblr.api.services.SubredditsService
import com.nikitagorbatko.humblr.data.name.NameRepository
import org.koin.mp.KoinPlatform

class SavedPostsPagingSource(
    private val token: String,
    private val nameRepository: NameRepository,
    private val service: SavedPostsService
) : PagingSource<String, ChildPostDto>() {
    private var myName: String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ChildPostDto> {
        val after = params.key ?: EMPTY_AFTER

        if (myName == null) {
            myName = nameRepository.fetchName()
        }

        return kotlin.runCatching {
            service.getSavedPosts(
                userName = myName ?: return LoadResult.Error(Throwable("-_-")),
                accessToken = token,
                after = after
            ).data
        }.fold(onSuccess = {
            LoadResult.Page(it.children, it.before, it.after)
        }, onFailure = {
            LoadResult.Error(it)
        })
    }

    override fun getRefreshKey(state: PagingState<String, ChildPostDto>) = EMPTY_AFTER

    companion object {
        private const val EMPTY_AFTER = ""
        const val PER_PAGE = 25
    }
}