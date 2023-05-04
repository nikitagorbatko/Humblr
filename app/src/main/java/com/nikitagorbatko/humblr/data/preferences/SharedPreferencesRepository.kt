package com.nikitagorbatko.humblr.data.preferences

interface SharedPreferencesRepository {
    fun getToken(): String?

    fun setToken(token: String)

    fun getIsFirstStart(): Boolean

    fun setIsNotFirstStart()

    fun logout()
}