package com.nikitagorbatko.humblr

import android.app.Application
import com.nikitagorbatko.humblr.api.DataSource
import com.nikitagorbatko.humblr.data.posts.PostsRepository
import com.nikitagorbatko.humblr.data.posts.PostsRepositoryImpl
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepositoryImpl
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepositoryImpl
import com.nikitagorbatko.humblr.domain.SubscribeUseCase
import com.nikitagorbatko.humblr.domain.UnsubscribeUseCase
import com.nikitagorbatko.humblr.ui.subreddit_posts.PostsViewModel
import com.nikitagorbatko.humblr.ui.subreddits.SubredditsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
//    private val auth = module {
//        single { AuthorizationService(androidContext()) }
//    }
//    private val ui = module {
//        viewModel { DashboardViewModel() }
//    }

    private val uiModule = module {
        viewModel { SubredditsViewModel(get(), get(), get()) }
        viewModel { PostsViewModel(get()) }
    }

    private val domainModule = module {
        factory { SubscribeUseCase(DataSource().redditService, get()) }
        factory { UnsubscribeUseCase(DataSource().redditService, get()) }
    }

    private val dataModule = module {
        single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(androidContext()) }
        single { get<SharedPreferencesRepository>().getToken() }
        single<SubredditsRepository> { SubredditsRepositoryImpl(get(), DataSource().redditService) }
        single<PostsRepository> {
            PostsRepositoryImpl(get(), DataSource().redditService)
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                domainModule,
                dataModule,
                uiModule
            )
        }
    }
}