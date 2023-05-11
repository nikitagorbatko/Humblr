package com.nikitagorbatko.humblr.ui.favourites

import androidx.lifecycle.ViewModel
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import com.nikitagorbatko.humblr.data.saved_comments.SavedCommentsRepository
import com.nikitagorbatko.humblr.data.saved_posts.SavedPostsRepository
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository
import com.nikitagorbatko.humblr.domain.GetFavouriteSubredditsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavouritesViewModel(
    private val subredditsRepository: SubredditsRepository,
    private val savedCommentsRepository: SavedCommentsRepository,
) : ViewModel() {
    fun getAllSubreddits() = subredditsRepository.getDefaultSubreddits()

    fun getFavouriteSubreddits() = subredditsRepository.getFavouriteSubreddits()

    fun getSavedComments() = savedCommentsRepository.getComments()
}