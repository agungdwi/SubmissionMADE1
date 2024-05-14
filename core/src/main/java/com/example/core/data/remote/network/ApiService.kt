package com.example.core.data.remote.network

import com.example.core.data.remote.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MovieListResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
    ): MovieListResponse
}