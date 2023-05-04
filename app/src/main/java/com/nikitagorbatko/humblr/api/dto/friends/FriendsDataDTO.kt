package com.nikitagorbatko.humblr.api.dto.friends

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendsDataDTO(
    @Json(name = "children") val children: List<FriendDTO>
)
