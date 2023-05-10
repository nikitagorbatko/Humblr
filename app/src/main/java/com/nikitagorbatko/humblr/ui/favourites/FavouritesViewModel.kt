package com.nikitagorbatko.humblr.ui.favourites

import androidx.lifecycle.ViewModel
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository
import com.nikitagorbatko.humblr.domain.GetFavouriteSubredditsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavouritesViewModel(
    private val subredditsRepository: SubredditsRepository,
    private val getFavouriteSubredditsUseCase: GetFavouriteSubredditsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _subreddits = MutableStateFlow<List<ChildSubredditDto>?>(null)
    val subreddits = _subreddits.asStateFlow()

    fun getAllSubreddits() = subredditsRepository.getDefaultSubreddits()

    fun getFavouriteSubreddits() = subredditsRepository.getFavouriteSubreddits()

//    suspend fun getFavouriteSubreddits() {
//        try {
//            _state.emit(State.LOADING)
//            val subreddits = getFavouriteSubredditsUseCase.execute()
//            _subreddits.emit(subreddits)
//            _state.emit(State.PRESENT)
//        } catch (_: Exception) {
//            _state.emit(State.ERROR)
//        }
//    }

    enum class State { LOADING, PRESENT, ERROR }
}