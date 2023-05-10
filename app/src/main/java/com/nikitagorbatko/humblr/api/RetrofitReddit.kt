package com.nikitagorbatko.humblr.api


import com.nikitagorbatko.humblr.api.pojos.*
import com.nikitagorbatko.humblr.api.services.*
import com.squareup.moshi.Moshi
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.*

interface RedditService {


    @GET("user/{username}/saved?limit=10&depth=1")
    suspend fun getSavedPosts(
        @Path("username") username: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): PostResponseDto

    @GET("comments/{id}?limit=10&depth=1")
    suspend fun getCommentsOfPost(
        @Path("id") postID: String,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Header("Authorization") accessToken: String
    ): List<PostResponseDto>

    @HTTP(method = "DELETE", path = "api/v1/me/friends/{uname}", hasBody = true)
    //@DELETE("api/v1/me/friends/{uname}")
    suspend fun removeFromFriends(
        @Path("uname") userName: String,
        @Body json: RequestBody,
        @Header("Authorization") accessToken: String
    ): Response<ResponseBody>

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
}

private const val BASE_URL = "https://oauth.reddit.com/"

class RetrofitReddit {
//    private val commentFactory: PolymorphicJsonAdapterFactory<CommentInterface> = PolymorphicJsonAdapterFactory.of(CommentInterface::class.java, "data")
//        .withSubtype(SetCommentDataDto::class.java, CommentType.SET_REPLIES.name)
//        .withSubtype(EmptyCommentDataDto::class.java, CommentType.EMPTY_REPLIES.name)

    private val moshi = Moshi.Builder()
        .add(SkipEmptyRepliesAdapter())
        //.add(commentFactory)
        .build()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()

    fun addFriendService(): AddFriendService = retrofit.create(AddFriendService::class.java)

    fun favouriteSubredditsService(): FavouriteSubredditsService =
        retrofit.create(FavouriteSubredditsService::class.java)

    fun defaultSubredditsService(): DefaultSubredditsService =
        retrofit.create(DefaultSubredditsService::class.java)

    fun friendService(): FriendService = retrofit.create(FriendService::class.java)

    fun meService(): MeService = retrofit.create(MeService::class.java)

    fun newSubredditsService(): NewSubredditsService =
        retrofit.create(NewSubredditsService::class.java)

    fun popularSubredditsService(): PopularSubredditsService =
        retrofit.create(PopularSubredditsService::class.java)

    fun postCommentsService(): PostCommentsService =
        retrofit.create(PostCommentsService::class.java)

    fun querySubredditsService(): QuerySubredditsService =
        retrofit.create(QuerySubredditsService::class.java)

    fun subredditsService(): SubredditsService = retrofit.create(SubredditsService::class.java)

    fun subscribeSubService(): SubscribeSubService =
        retrofit.create(SubscribeSubService::class.java)

    fun unsubscribeSubService(): UnsubscribeSubService =
        retrofit.create(UnsubscribeSubService::class.java)

    fun userCommentsService(): UserCommentsService =
        retrofit.create(UserCommentsService::class.java)

    fun userInfoService(): UserInfoService = retrofit.create(UserInfoService::class.java)
}