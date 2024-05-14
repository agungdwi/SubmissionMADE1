package com.example.core.utils

import com.example.core.data.local.entity.MovieEntity
import com.example.core.data.local.entity.MovieFavoriteEntity
import com.example.core.data.remote.response.MovieResponse
import com.example.core.domain.model.Movie


object DataMapper {
    fun mapEntitiesToDomain(input: MovieEntity): Movie =

        Movie(
                id = input.id,
                title = input.title,
                overview = input.overview,
                posterPath = input.posterPath,
                backdropPath =  input.backdropPath,
                voteAverage = input.voteAverage,
                releaseDate = input.releaseDate,
                popularity = input.popularity,
                page = input.page
            )

    fun mapFavEntitiesToDomain(input: MovieFavoriteEntity): Movie =
        Movie(

            id = input.id,
            title = input.title,
            overview = input.overview,
            posterPath = input.posterPath,
            backdropPath =  input.backdropPath,
            voteAverage = input.voteAverage,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            page = 0
        )

    fun mapResponseToDomain(input: List<MovieResponse>): List<Movie> {
        val movieList = ArrayList<Movie>()
        input.map{
            val response = Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath ?: "",
                backdropPath = it.backdropPath ?: "",
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                page = 0
            )
            movieList.add(response)
        }
        return movieList
    }

    fun mapResponseToEntity(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map{
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath ?: "",
                backdropPath = it.backdropPath ?: "",
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
            )
            movieList.add(movie)
        }
        return  movieList
    }

    fun mapDomainToFavEntity(input: Movie) = MovieFavoriteEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        voteAverage = input.voteAverage,
        releaseDate = input.releaseDate,
        popularity = input.popularity,
    )

}