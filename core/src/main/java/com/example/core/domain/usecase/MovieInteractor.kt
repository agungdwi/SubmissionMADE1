package com.example.core.domain.usecase

import com.example.core.domain.repository.IMovieRepository
import com.example.core.domain.model.Movie

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getMovies() = movieRepository.getMovies()
    override fun searchMovies(query: String) = movieRepository.searchMovies(query)
    override fun getFavoriteMovies() = movieRepository.getFavoriteMovies()
    override fun isMovieFavorite(movieId: Int) = movieRepository.isMovieFavorite(movieId)

    override suspend fun insertFavoriteMovie(movie: Movie) = movieRepository.insertFavoriteMovie(movie)

    override suspend fun deleteFavoriteMovie(movie: Movie) = movieRepository.deleteFavoriteMovie(movie)
}