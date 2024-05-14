package com.example.core.data.remote

import android.util.Log
import com.example.core.data.remote.network.ApiResponse
import com.example.core.data.remote.network.ApiService
import com.example.core.data.remote.response.MovieResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun searchMovies(query: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.searchMovies(query)

                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}