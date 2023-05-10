package com.nikitagorbatko.humblr.api.pojos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChildSubredditDto(
    @Json(name = "kind")  val kind: String,
    @Json(name = "data")  val data: SubredditDto
)

@JsonClass(generateAdapter = true)
data class SubDataDto(
    @Json(name = "after") val after: String?,
    @Json(name = "before") val before: String?,
    @Json(name = "children") val children: List<ChildSubredditDto>
)

@JsonClass(generateAdapter = true)
data class SubredditDto(
    @Json(name = "title") val title: String,
    @Json(name = "display_name_prefixed") val namePrefixed: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "user_is_subscriber") var subscribed: Boolean,
    @Json(name = "subscribers") val subscribers: Long,
    @Json(name = "display_name") val displayName: String,
)

@JsonClass(generateAdapter = true)
data class SubResponseDto(
    @Json(name = "kind")val kind: String,
    @Json(name = "data")val data: SubDataDto
)
