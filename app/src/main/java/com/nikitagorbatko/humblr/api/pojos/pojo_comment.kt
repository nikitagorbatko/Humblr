package com.nikitagorbatko.humblr.api.pojos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentDto(
    @Json(name = "kind") var kind: String?,
    @Json(name = "data") var data: CommentDataDto?
)

@JsonClass(generateAdapter = true)
data class CommentsResponseDto(
    @Json(name = "kind") var kind: String?,
    @Json(name = "data") var dataDto: DataDto?
)

@JsonClass(generateAdapter = true)
data class DataDto(
    @Json(name = "after") var after: String?,
    @Json(name = "dist") var dist: String?,
    @Json(name = "children") var children: List<CommentDto>?,
    @Json(name = "before") var before: String?
)

@JsonClass(generateAdapter = true)
data class GildingsDto(
    @Json(name = "kind") var kind: String?,
)

@JsonClass(generateAdapter = true)
data class CommentDataDto(
    @Json(name = "id") var id: String?,
    @Json(name = "subreddit") var subreddit: String?,
    @Json(name = "selftext") var selftext: String?,
    @Json(name = "saved") var saved: Boolean?,
    @Json(name = "title") var title: String?,
    @Json(name = "downs") var downs: Int?,
    @Json(name = "name") var name: String?,
    @Json(name = "upvote_ratio") var upvoteRatio: Double?,
    @Json(name = "body") var body: String?,
    @Json(name = "author_fullname") var authorFullname: String?,
    @Json(name = "likes") var likes: String?,
    @Json(name = "author") var author: String?,
    @Json(name = "created_utc") var createdUtc: Int?,
    @Json(name = "created") var created: Int?,
    @Json(name = "num_comments") var numComments: Int?,
    @Json(name = "replies") var replies: RepliesDto?,
    @Json(name = "url") var url: String?,
    @Json(name = "is_video") var isVideo: Boolean?
)


@JsonClass(generateAdapter = true)
data class RepliesDto(
    @Json(name = "kind") val kind: String?,
    @Json(name = "data") val data: DataDto?
)

