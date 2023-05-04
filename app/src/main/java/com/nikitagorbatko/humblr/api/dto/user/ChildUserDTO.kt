package com.nikitagorbatko.humblr.api.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChildUserDTO(
    @Json(name = "kind") val kind: String,
    @Json(name = "data") val data: UserDTO
)
