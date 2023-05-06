package com.nikitagorbatko.humblr.api.dto

import com.squareup.moshi.FromJson

class SkipEmptyRepliesAdapter {

    @FromJson
    fun fromJson(response: CommentData): CommentData {
        if (response.replies is CommentData) {
            val parsedReply = response.replies as CommentsResponse
            response.parsedReplies = parsedReply
        }
        return response
    }
}