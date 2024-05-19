package com.example.moviedb.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {



    fun checkFavorite(id: Int) = movieUseCase.isMovieFavorite(id).asLiveData()

    fun setFavorite(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.insertFavoriteMovie(movie)
        }

    }

    fun deleteFavorite(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.deleteFavoriteMovie(movie)
        }


    }


//    suspend fun checkFavorite(id: Int) = movieUseCase.isMovieFavorite(id)
}