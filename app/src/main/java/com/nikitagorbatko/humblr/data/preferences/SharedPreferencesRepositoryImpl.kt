package com.nikitagorbatko.humblr.data.preferences

import android.content.Context
import android.content.SharedPreferences

private const val PREFERENCES_NAME = "auth_data"
private const val TOKEN_KEY = "token_key"
private const val IS_FIRST_START_KEY = "is_first_start_key"

class SharedPreferencesRepositoryImpl constructor(context: Context) :
    SharedPreferencesRepository {
    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun getToken(): String? {
        return preferences.getString(TOKEN_KEY, null)
    }

    override fun setToken(token: String) {
        preferences.edit().putString(TOKEN_KEY, token).apply()
    }

    override fun setIsNotFirstStart() {
        preferences.edit().putBoolean(IS_FIRST_START_KEY, false).apply()
    }

    override fun logout() {
        preferences.edit().putString(TOKEN_KEY, null).apply()
    }

    override fun getIsFirstStart(): Boolean {
        return preferences.getBoolean(IS_FIRST_START_KEY, true)
    }
}