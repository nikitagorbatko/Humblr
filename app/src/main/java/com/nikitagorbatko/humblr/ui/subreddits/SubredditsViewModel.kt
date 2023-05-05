package com.nikitagorbatko.humblr.ui.subreddits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository
import com.nikitagorbatko.humblr.domain.SubscribeUseCase
import com.nikitagorbatko.humblr.domain.UnsubscribeUseCase

class SubredditsViewModel(
    private val repository: SubredditsRepository,
    private val subscribeUseCase: SubscribeUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase
) : ViewModel() {
    fun getPopularSubreddits() = repository.getPopularSubreddits().cachedIn(viewModelScope)

    fun getNewSubreddits() = repository.getNewSubreddits().cachedIn(viewModelScope)

    fun getQuerySubreddits(query: String) =
        repository.getQuerySubreddits(query = query).cachedIn(viewModelScope)

    suspend fun subscribeToSub(subname: String) {
        subscribeUseCase.execute(subname)
    }

    suspend fun unsubscribeFromSub(subname: String) {
        unsubscribeUseCase.execute(subname)
    }
}