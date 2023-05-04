package com.nikitagorbatko.humblr.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.humblr.data.subreddits.SubredditsRepository

class SubredditsViewModel(private val token: String, private val repository: SubredditsRepository) :
    ViewModel() {

    fun getPhotosBySearch() =
        repository.getPopularSubreddits(token = token).cachedIn(viewModelScope)
}