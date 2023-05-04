package com.nikitagorbatko.humblr.api.dto.subreddit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubResponseDTO(
    @Json(name = "kind")val kind: String,
    @Json(name = "data")val data: SubDataDTO
)
