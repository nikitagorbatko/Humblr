package com.nikitagorbatko.humblr.api.dto.subreddit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubredditDTO(
    @Json(name = "title") val title: String,
    @Json(name = "display_name_prefixed") val namePrefixed: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "user_is_subscriber") var subscribed: Boolean,
    @Json(name = "subscribers")  val subscribers: Long
)