package com.nikitagorbatko.humblr

import android.app.Application
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepositoryImpl
import com.nikitagorbatko.humblr.ui.dashboard.DashboardViewModel
import net.openid.appauth.AuthorizationService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
//    private val auth = module {
//        single { AuthorizationService(androidContext()) }
//    }
//    private val ui = module {
//        viewModel { DashboardViewModel() }
//    }

    private val dataModule = module {
        single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(androidContext()) }
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