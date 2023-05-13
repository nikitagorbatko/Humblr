package com.nikitagorbatko.humblr.ui.post

import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.api.pojos.RepliesDto

object CommentDtoToUiMapper {
    fun convert(list: List<CommentDto>?): List<CommentUi> {
        val commentsUi = mutableListOf<CommentUi>()
        list?.forEach { commentDto ->
            val data = commentDto.data ?: return@forEach
            commentsUi.add(
                CommentUi(
                    nodeViewId = data.id!!,
                    id = data.id,
                    subreddit = data.subreddit,
                    selftext = data.selftext,
                    saved = data.saved,
                    title = data.title,
                    downs = data.downs,
                    name = data.name,
                    upvoteRatio = data.upvoteRatio,
                    body = if (data.body == null) return@forEach else data.body,
                    authorFullname = data.authorFullname,
                    //likes = data.likes,
                    author = data.author,
                    createdUtc = data.createdUtc,
                    created = data.created,
                    numComments = data.numComments,
                    replies = convertRepliesDto(data.replies),
                    url = data.url,
                    isVideo = data.isVideo,
                )
            )
        }
        return commentsUi
    }

    private fun convertRepliesDto(repliesDto: RepliesDto?): List<CommentUi> {
        val commentsUi = mutableListOf<CommentUi>()
        repliesDto?.data?.children?.forEach { commentDto ->
            val data = commentDto.data ?: return@forEach
            commentsUi.add(
                CommentUi(
                    nodeViewId = data.id!!,
                    id = data.id,
                    subreddit = data.subreddit,
                    selftext = data.selftext,
                    saved = data.saved,
                    title = data.title,
                    downs = data.downs,
                    name = data.name,
                    upvoteRatio = data.upvoteRatio,
                    body = if (data.body == null) return@forEach else data.body,
                    authorFullname = data.authorFullname,
                    //likes = data.likes,
                    author = if (data.author == "[deleted]") {
                        return@forEach
                    } else {
                        data.author
                    },
                    createdUtc = data.createdUtc,
                    created = data.created,
                    numComments = data.numComments,
                    replies = convertRepliesDto(data.replies),
                    url = data.url,
                    isVideo = data.isVideo,
                )
            )
        }
        return commentsUi
    }
}