package com.nikitagorbatko.humblr.ui.account

import androidx.lifecycle.ViewModel
import com.nikitagorbatko.humblr.api.pojos.UserDto
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.domain.GetAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    private val _account = MutableStateFlow<UserDto?>(null)
    val account = _account.asStateFlow()

    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    suspend fun getUserInfo() {
        try {
            val account = getAccountUseCase.execute()
            _account.emit(account)
            _state.emit(State.PRESENT)
        } catch (_: Exception) {
            _state.emit(State.ERROR)
        }
    }

    fun logout() {
        sharedPreferencesRepository.logout()
    }

    enum class State { LOADING, PRESENT, ERROR }
}