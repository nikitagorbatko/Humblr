package com.nikitagorbatko.humblr.api


import com.nikitagorbatko.humblr.api.services.*
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://oauth.reddit.com/"

class RetrofitReddit {
//    private val commentFactory: PolymorphicJsonAdapterFactory<CommentInterface> = PolymorphicJsonAdapterFactory.of(CommentInterface::class.java, "data")
//        .withSubtype(SetCommentDataDto::class.java, CommentType.SET_REPLIES.name)
//        .withSubtype(EmptyCommentDataDto::class.java, CommentType.EMPTY_REPLIES.name)

//    private val moshi = Moshi.Builder()
//        //.add()
//        .add(SkipEmptyRepliesAdapter())
//        .build()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())//.asLenient())
        .build()

    fun addFriendService(): FriendUnfriendService = retrofit.create(FriendUnfriendService::class.java)

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

    fun savedCommentsService(): SavedCommentsService =
        retrofit.create(SavedCommentsService::class.java)

    fun savedPostsService(): SavedPostsService = retrofit.create(SavedPostsService::class.java)

    fun subredditsService(): SubredditsService = retrofit.create(SubredditsService::class.java)

    fun subscribeSubService(): SubscribeSubService =
        retrofit.create(SubscribeSubService::class.java)

    fun unsubscribeSubService(): UnsubscribeSubService =
        retrofit.create(UnsubscribeSubService::class.java)

    fun userCommentsService(): UserCommentsService =
        retrofit.create(UserCommentsService::class.java)

    fun userInfoService(): UserInfoService = retrofit.create(UserInfoService::class.java)

    fun voteUnvoteService(): VoteUnvoteService = retrofit.create(VoteUnvoteService::class.java)

    fun saveUnsaveCommentService(): SaveUnsaveService = retrofit.create(SaveUnsaveService::class.java)
}