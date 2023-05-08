package com.nikitagorbatko.humblr.api


import com.nikitagorbatko.humblr.api.dto.CommentsResponse
import com.nikitagorbatko.humblr.api.dto.SkipEmptyRepliesAdapter
import com.nikitagorbatko.humblr.api.dto.friends.FriendResponseDTO
import com.nikitagorbatko.humblr.api.dto.post.PostResponseDTO
import com.nikitagorbatko.humblr.api.dto.subreddit.SubResponseDTO
import com.nikitagorbatko.humblr.api.dto.user.ChildUserDTO
import com.nikitagorbatko.humblr.api.dto.user.UserDTO
import com.nikitagorbatko.humblr.domain.FriendUserUseCase
import com.squareup.moshi.Moshi
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface RedditService {
    @GET("subreddits/new")
    suspend fun getNewSubreddits(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("count") count: Int = 0,
        @Header("Authorization") accessToken: String
    ): SubResponseDTO

    @GET("subreddits/popular")
    suspend fun getPopularSubreddits(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): SubResponseDTO

    @GET("/subreddits/search")
    suspend fun getQuerySubreddits(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("q") query: String,
        @Header("Authorization") accessToken: String
    ): SubResponseDTO


    @GET("/user/{username}/comments")
    suspend fun getUserComments(
        @Path("username") userName: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): CommentsResponse

    @GET("subreddits/mine/subscriber")
    suspend fun getFavoriteSubreddits(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): SubResponseDTO

    @GET("user/{username}/saved?limit=${DataSource.ITEMS_ON_PAGE}&depth=1")
    suspend fun getSavedPosts(
        @Path("username") username: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): PostResponseDTO

    @GET("subreddits/search?limit=${DataSource.ITEMS_ON_PAGE}")
    suspend fun getSubredditsByQuery(
        @Query("q") query: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): SubResponseDTO

    @GET("comments/{id}?limit=${DataSource.ITEMS_ON_PAGE}&depth=1")
    suspend fun getCommentsOfPost(
        @Path("id") postID: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): List<PostResponseDTO>

    @GET("r/{displayName}")
    suspend fun getSubredditPosts(
        @Path("displayName") displayName: String,
        @Header("Authorization") accessToken: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
    ): PostResponseDTO

    @GET("comments/{id}")
    suspend fun getPostComments(
        @Path("id") id: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): List<CommentsResponse>

    @GET("user/{name}/about")
    suspend fun getUserInfo(
        @Path("name") userName: String,
        @Header("Authorization") accessToken: String
    ): ChildUserDTO

    @POST("api/subscribe?action=sub")
    suspend fun subscribeToSub(
        @Query("sr") subName: String,
        @Header("Authorization") token: String
    )

    @POST("api/subscribe?action=unsub")
    suspend fun unsubscribeFromSub(
        @Query("sr") subName: String,
        @Header("Authorization") token: String
    )

    @PUT("api/v1/me/friends/{uname}")
    suspend fun addAsFriend(
        @Path("uname") userName: String,
        @Body json: RequestBody,
        @Header("Authorization") accessToken: String
    ): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "api/v1/me/friends/{uname}", hasBody = true)
    //@DELETE("api/v1/me/friends/{uname}")
    suspend fun removeFromFriends(
        @Path("uname") userName: String,
        @Body json: RequestBody,
        @Header("Authorization") accessToken: String
    ): Response<ResponseBody>

    @GET("api/v1/me/friends")
    suspend fun getFriends(
        @Header("Authorization") accessToken: String
    ): FriendResponseDTO

    @POST("api/save")
    suspend fun savePost(
        @Query("id") postID: String,
        @Header("Authorization") accessToken: String
    )

    @POST("api/unsave")
    suspend fun unsavePost(
        @Query("id") postID: String,
        @Header("Authorization") accessToken: String
    )

    @GET("/api/v1/me")
    suspend fun getAboutMe(
        @Header("Authorization") accessToken: String
    ): UserDTO
}

class DataSource {
    companion object {
        private const val BASE_URL = "https://oauth.reddit.com/"
        const val ITEMS_ON_PAGE = 10
    }

    private val moshi = Moshi.Builder()
        .add(SkipEmptyRepliesAdapter())
        .build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    val redditService = retrofit.create(RedditService::class.java)
}