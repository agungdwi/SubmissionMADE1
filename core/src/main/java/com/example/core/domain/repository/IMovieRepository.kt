package com.example.core.domain.repository

import androidx.paging.PagingData
import com.example.core.data.Resource
import com.example.core.domain.model.Movie


import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<Resource<List<Movie>>>
    fun getFavoriteMovies():Flow<List<Movie>>
    fun isMovieFavorite(movieId: Int): Flow<Boolean>
    suspend fun insertFavoriteMovie(movie: Movie)
    suspend fun deleteFavoriteMovie(movie: Movie): Int

}