package com.nikitagorbatko.humblr.api.dto.friends

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendResponseDTO(
    @Json(name = "kind") val kind: String,
    @Json(name = "data") val data: FriendsDataDTO
)
