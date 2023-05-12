package com.nikitagorbatko.humblr.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import com.nikitagorbatko.humblr.api.services.VoteUnvoteService
import com.nikitagorbatko.humblr.data.saved_comments.SavedCommentsRepository
import com.nikitagorbatko.humblr.data.saved_posts.SavedPostsRepository
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository
import com.nikitagorbatko.humblr.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val subredditsRepository: SubredditsRepository,
    private val savedCommentsRepository: SavedCommentsRepository,
    private val subscribeUseCase: SubscribeUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase,
    private val voteUnvoteUseCase: VoteUnvoteUseCase,
    private val saveUnsaveCommentUseCase: SaveUnsaveCommentUseCase
) : ViewModel() {
    fun getAllSubreddits() = subredditsRepository.getDefaultSubreddits()

    fun getFavouriteSubreddits() = subredditsRepository.getFavouriteSubreddits()

    fun getSavedComments() = savedCommentsRepository.getComments()

    suspend fun subscribeToSub(subname: String) {
        subscribeUseCase.execute(subname)
    }

    suspend fun unsubscribeFromSub(subname: String) {
        unsubscribeUseCase.execute(subname)
    }

    //does not check the response
    fun voteUp(id: String) {
        viewModelScope.launch {
            voteUnvoteUseCase.execute(id = id, dir = VoteUnvoteService.voteUp)
        }
    }

    //does not check the response
    fun voteDown(id: String) {
        viewModelScope.launch {
            voteUnvoteUseCase.execute(id = id, dir = VoteUnvoteService.voteDown)
        }
    }

    //does not check the response
    fun saveComment(id: String) {
        viewModelScope.launch {
            saveUnsaveCommentUseCase.executeSave(id)
        }
    }

    //does not check the response
    fun unsaveComment(id: String) {
        viewModelScope.launch {
            saveUnsaveCommentUseCase.executeUnsave(id)
        }
    }
}