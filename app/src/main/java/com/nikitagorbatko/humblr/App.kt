package com.nikitagorbatko.humblr

import android.app.Application
import com.nikitagorbatko.humblr.api.DataSource
import com.nikitagorbatko.humblr.api.RedditService
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepositoryImpl
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepositoryImpl
import com.nikitagorbatko.humblr.ui.dashboard.DashboardViewModel
import com.nikitagorbatko.humblr.ui.home.SubredditsViewModel
import net.openid.appauth.AuthorizationService
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
        viewModel { (token: String) -> SubredditsViewModel(token, get()) }
    }

    private val dataModule = module {
        single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(androidContext()) }
        single<SubredditsRepository> { (token: String) ->  SubredditsRepositoryImpl(token, DataSource().redditService) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
            )
        }
    }
}