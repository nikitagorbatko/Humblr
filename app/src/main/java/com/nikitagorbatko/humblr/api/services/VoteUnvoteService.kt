package com.nikitagorbatko.humblr.api.services

import com.nikitagorbatko.humblr.api.pojos.ChildUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VoteUnvoteService {

    @POST("/api/vote")
    suspend fun vote(
        @Query("id") id: String,
        @Query("dir") dir: Int,
        @Header("Authorization") accessToken: String
    )

    companion object {
        const val voteUp = 1
        const val voteDown = -1
    }
}