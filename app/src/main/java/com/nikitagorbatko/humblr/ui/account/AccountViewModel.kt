package com.nikitagorbatko.humblr.ui.account

import androidx.lifecycle.ViewModel
import com.nikitagorbatko.humblr.api.pojos.ChildUserDto
import com.nikitagorbatko.humblr.api.pojos.UserDto
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.domain.GetAccountUseCase
import com.nikitagorbatko.humblr.domain.GetAllCommentsUseCase
import com.nikitagorbatko.humblr.domain.GetUserCommentsAmountUseCase
import com.nikitagorbatko.humblr.domain.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val getUserCommentsAmountUseCase: GetUserCommentsAmountUseCase

) : ViewModel() {
    private val _account = MutableStateFlow<UserDto?>(null)
    val account = _account.asStateFlow()

    private val _commentsAmount = MutableStateFlow(0)
    val commentsAmount = _commentsAmount.asStateFlow()

    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    suspend fun getUserInfo() {
        var account: UserDto? = null
        try {
            account = getAccountUseCase.execute()
            _account.emit(account)
            _state.emit(State.PRESENT)
        } catch (_: Exception) {
            _state.emit(State.ERROR)
        }

        try {
            account?.name?.let {
                _commentsAmount.emit(getUserCommentsAmountUseCase.execute(it))
            }
        } catch (_: Exception) { }
    }

    fun logout() {
        sharedPreferencesRepository.logout()
    }

    enum class State { LOADING, PRESENT, ERROR }
}