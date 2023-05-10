package com.nikitagorbatko.humblr.api.pojos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendDto(
    @Json(name = "name") val name: String,
    @Json(name = "id") val id: String
)

@JsonClass(generateAdapter = true)
data class FriendResponseDto(
    @Json(name = "kind") val kind: String,
    @Json(name = "data") val data: FriendsDataDto
)

@JsonClass(generateAdapter = true)
data class FriendsDataDto(
    @Json(name = "children") val children: List<FriendDto>
)
