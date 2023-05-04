package com.nikitagorbatko.humblr.api.dto.post

import com.nikitagorbatko.humblr.api.dto.post.PostDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChildPostDTO(
    @Json(name = "kind") val kind: String,
    @Json(name = "data") val data: PostDTO
)
