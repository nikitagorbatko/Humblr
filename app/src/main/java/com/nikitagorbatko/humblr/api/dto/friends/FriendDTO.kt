package com.nikitagorbatko.humblr.api.dto.friends

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendDTO(
    @Json(name = "name") val name: String,
    @Json(name = "id") val id: String
)
