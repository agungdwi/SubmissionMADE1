package com.example.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.room.DatabaseTransactionHelper
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.network.ApiResponse
import com.example.core.data.remote.network.ApiService
import com.example.core.domain.repository.IMovieRepository
import com.example.core.domain.model.Movie
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val apiService: ApiService,
    private val transactionHelper: DatabaseTransactionHelper
) : IMovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,

            ),
            remoteMediator = MovieRemoteMediator(apiService, localDataSource,transactionHelper),
            pagingSourceFactory = { localDataSource.getAllMovies() }
        ).flow.map { pagingData ->
            pagingData.map { movieEntity ->
                DataMapper.mapEntitiesToDomain(movieEntity)
            }
            }

    }

    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.searchMovies(query).first()) {
                is ApiResponse.Success -> {
                    val movies = DataMapper.mapResponseToDomain(apiResponse.data)
                    emit(Resource.Success(movies))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList()))
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }

        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map{ entity->
            entity.map {
                DataMapper.mapFavEntitiesToDomain(it)
            }
        }

    }

    override fun isMovieFavorite(movieId: Int): Flow<Boolean> {
        val result = localDataSource.isMovieInDatabase(movieId)

        return result.map { it != 0 }
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        val movieEntity = DataMapper.mapDomainToFavEntity(movie)
        return localDataSource.insertFavoriteMovie(movieEntity)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) : Int {
        val movieEntity = DataMapper.mapDomainToFavEntity(movie)

        return localDataSource.deleteFavoriteMovie(movieEntity)
    }

}