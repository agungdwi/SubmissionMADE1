package com.example.moviedb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.domain.usecase.MovieUseCase


class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movies = movieUseCase.getMovies().cachedIn(viewModelScope).asLiveData()



}