package com.nikitagorbatko.humblr.api.dto.post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostDataDTO(
    @Json(name = "after")  val after: String?,
    @Json(name = "before")  val before: String?,
    @Json(name = "children")  val children: List<ChildPostDTO>
)