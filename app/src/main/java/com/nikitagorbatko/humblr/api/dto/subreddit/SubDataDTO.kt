package com.nikitagorbatko.humblr.api.dto.subreddit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubDataDTO(
    @Json(name = "after") val after: String?,
    @Json(name = "before") val before: String?,
    @Json(name = "children") val children: List<ChildSubredditDTO>
)
