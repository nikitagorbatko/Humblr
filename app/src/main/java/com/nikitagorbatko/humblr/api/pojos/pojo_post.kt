package com.nikitagorbatko.humblr.api.pojos

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ChildPostDto(
    @Json(name = "kind") val kind: String,
    @Json(name = "data") val data: PostDto
): Parcelable

@JsonClass(generateAdapter = true)
data class PostDataDto(
    @Json(name = "after")  val after: String?,
    @Json(name = "before")  val before: String?,
    @Json(name = "children")  val children: List<ChildPostDto>
)

@Parcelize

@JsonClass(generateAdapter = true)
data class PostDto(
    @Json(name = "subreddit")  val subreddit: String?,
    @Json(name = "subreddit_name_prefixed")  val subredditPrefixed: String?,
    @Json(name = "author")  val author: String?,
    @Json(name = "author_fullname")  val author_fullname: String?,
    @Json(name = "created_utc")  val created_utc: Double?,
    @Json(name = "title")  val title: String?,
    @Json(name = "body")  val body: String?,
    @Json(name = "name")  val name: String,
    @Json(name = "score")  val score: Int?,
    @Json(name = "thumbnail")  val thumbnail: String?,
    @Json(name = "url")  val url: String?,
    @Json(name = "id")  val id: String,
    @Json(name = "is_video")  val is_video: Boolean?,
    @Json(name = "ups")  var ups: Int?,
    @Json(name = "num_comments")  val num_comments: Int?,
    @Json(name = "saved")  var saved: Boolean?,
    @Json(name = "subreddit_subscribers")  val subreddit_subscribers: Int?,
    @Json(name = "thumbnail_height")  val thumbnail_height: Int?,
    @Json(name = "thumbnail_width")  val thumbnail_width: Int?,
    @Json(name = "likes")  var likes: Boolean?
): Parcelable

@JsonClass(generateAdapter = true)
data class PostResponseDto(
    @Json(name = "kind")val kind: String,
    @Json(name = "data")val data: PostDataDto
)
