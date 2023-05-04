package com.nikitagorbatko.humblr.api.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "id")  val id: String,
    @Json(name = "name")  val name: String,
    @Json(name = "icon_img")  val icon_img: String,
    @Json(name = "total_karma")  val totalKarma: Int,
    @Json(name = "is_friend")  var isFriend: Boolean?
)
