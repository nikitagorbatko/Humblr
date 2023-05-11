package com.nikitagorbatko.humblr

import android.app.Application
import com.nikitagorbatko.humblr.api.RetrofitReddit
import com.nikitagorbatko.humblr.data.comments.CommentsRepository
import com.nikitagorbatko.humblr.data.comments.CommentsRepositoryImpl
import com.nikitagorbatko.humblr.data.friends.FriendsRepository
import com.nikitagorbatko.humblr.data.friends.FriendsRepositoryImpl
import com.nikitagorbatko.humblr.data.name.NameRepository
import com.nikitagorbatko.humblr.data.name.NameRepositoryImpl
import com.nikitagorbatko.humblr.data.posts.PostsRepository
import com.nikitagorbatko.humblr.data.posts.PostsRepositoryImpl
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepositoryImpl
import com.nikitagorbatko.humblr.data.saved_comments.SavedCommentsRepository
import com.nikitagorbatko.humblr.data.saved_comments.SavedCommentsRepositoryImpl
import com.nikitagorbatko.humblr.data.saved_posts.SavedPostsRepository
import com.nikitagorbatko.humblr.data.saved_posts.SavedPostsRepositoryImpl
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepositoryImpl
import com.nikitagorbatko.humblr.data.user_comments.UserCommentsRepository
import com.nikitagorbatko.humblr.data.user_comments.UserCommentsRepositoryImpl
import com.nikitagorbatko.humblr.domain.*
import com.nikitagorbatko.humblr.ui.account.AccountViewModel
import com.nikitagorbatko.humblr.ui.favourites.FavouritesViewModel
import com.nikitagorbatko.humblr.ui.friends.FriendsViewModel
import com.nikitagorbatko.humblr.ui.post.SinglePostViewModel
import com.nikitagorbatko.humblr.ui.subreddit_posts.PostsViewModel
import com.nikitagorbatko.humblr.ui.subreddits.SubredditsViewModel
import com.nikitagorbatko.humblr.ui.user.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    private val apiModule = module {
        single { RetrofitReddit() }
        single { get<RetrofitReddit>().addFriendService() }
        single { get<RetrofitReddit>().defaultSubredditsService() }
        single { get<RetrofitReddit>().favouriteSubredditsService() }
        single { get<RetrofitReddit>().friendService() }
        single { get<RetrofitReddit>().meService() }
        single { get<RetrofitReddit>().newSubredditsService() }
        single { get<RetrofitReddit>().popularSubredditsService() }
        single { get<RetrofitReddit>().postCommentsService() }
        single { get<RetrofitReddit>().querySubredditsService() }
        single { get<RetrofitReddit>().savedCommentsService() }
        single { get<RetrofitReddit>().savedPostsService() }
        single { get<RetrofitReddit>().subredditsService() }
        single { get<RetrofitReddit>().subscribeSubService() }
        single { get<RetrofitReddit>().unsubscribeSubService() }
        single { get<RetrofitReddit>().userCommentsService() }
        single { get<RetrofitReddit>().userInfoService() }
    }

    private val uiModule = module {
        viewModel { SubredditsViewModel(get(), get(), get()) }
        viewModel { PostsViewModel(get()) }
        viewModel { SinglePostViewModel(get()) }
        viewModel { UserViewModel(get(), get(), get()) }
        viewModel { AccountViewModel(get(), get()) }
        viewModel { FriendsViewModel(get()) }
        viewModel { FavouritesViewModel(get(), get()) }
    }

    private val domainModule = module {
        factory { SubscribeUseCase(get(), get()) }
        factory { UnsubscribeUseCase(get(), get()) }
        factory { GetAllCommentsUseCase(get(), get()) }
        factory { GetUserUseCase(get(), get()) }
        factory { FriendUserUseCase(get(), get()) }
        factory { GetAccountUseCase(get(), get()) }
        factory { GetFavouriteSubredditsUseCase(get(), get()) }
    }

    private val dataModule = module {
        single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(androidContext()) }
        single { get<SharedPreferencesRepository>().getToken() }
        single<SubredditsRepository> { SubredditsRepositoryImpl(get(), get(), get(), get(), get(), get()) }
        single<PostsRepository> {
            PostsRepositoryImpl(get(), get())
        }
        single<CommentsRepository> { CommentsRepositoryImpl(get()) }
        single<UserCommentsRepository> { UserCommentsRepositoryImpl(get(), get()) }
        single<FriendsRepository> { FriendsRepositoryImpl(get(), get()) }
        single<SavedPostsRepository> { SavedPostsRepositoryImpl(get(), get(), get()) }
        single<SavedCommentsRepository> { SavedCommentsRepositoryImpl(get(), get(), get()) }
        single<NameRepository> { NameRepositoryImpl(get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                apiModule,
                domainModule,
                dataModule,
                uiModule
            )
        }
    }

    companion object {
        const val NAME_PROPERTY = "name"
    }
}