package com.nikitagorbatko.humblr.api.dto.post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostResponseDTO(
    @Json(name = "kind")val kind: String,
    @Json(name = "data")val data: PostDataDTO
)
