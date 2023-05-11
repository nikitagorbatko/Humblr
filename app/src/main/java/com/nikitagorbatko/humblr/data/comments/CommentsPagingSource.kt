//package com.nikitagorbatko.humblr.data.comments
//
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.nikitagorbatko.humblr.api.RedditService
//import com.nikitagorbatko.humblr.api.pojos.CommentsResponseDto
//import com.nikitagorbatko.humblr.api.services.PostCommentsService
//
//class CommentsPagingSource(
//    private val id: String,
//    private val token: String,
//    private val service: PostCommentsService,
//) : PagingSource<String, CommentsResponseDto>() {
//
//    override suspend fun load(params: LoadParams<String>): LoadResult<String, CommentsResponseDto> {
//        val after = params.key ?: EMPTY_AFTER
//
//        return kotlin.runCatching {
//            service.getPostComments(
//                id = id,
//                accessToken = token,
//                after = after,
//            )
//        }.fold(onSuccess = {
//            LoadResult.Page(it, , it.after)
//        }, onFailure = {
//            LoadResult.Error(it)
//        })
//    }
//
//    override fun getRefreshKey(state: PagingState<String, CommentsResponseDto>) = EMPTY_AFTER
//
//    companion object {
//        private const val EMPTY_AFTER = ""
//        const val PER_PAGE = 25
//    }
//}