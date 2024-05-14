package com.example.moviedb.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.core.domain.usecase.MovieUseCase

class SearchViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    private val title: MutableLiveData<String> = MutableLiveData()

    fun searchMovies(query: String) {
        // Set the value only if it's different from the current value
        if (title.value != query) {
            title.value = query
        }
    }

    val movies = title.switchMap { query ->
        movieUseCase.searchMovies(query).asLiveData()
    }

}