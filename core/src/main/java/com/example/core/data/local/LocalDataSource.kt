package com.example.core.data.local

import com.example.core.data.local.entity.MovieEntity
import com.example.core.data.local.entity.MovieFavoriteEntity
import com.example.core.data.local.room.MovieDao
import com.example.core.data.local.entity.RemoteKeys
import com.example.core.data.local.room.MovieFavoriteDao
import com.example.core.data.local.room.RemoteKeysDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao, private val remoteKeysDao: RemoteKeysDao , private val movieFavoriteDao: MovieFavoriteDao) {

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun getAllMovies() = movieDao.getAllMovies()

    suspend fun deleteAllMovies()= movieDao.deleteAll()


    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) = remoteKeysDao.insertAll(remoteKeys)

    suspend fun getRemoteKeysId(id: String) = remoteKeysDao.getRemoteKeysId(id)
    suspend fun deleteRemoteKeys() = remoteKeysDao.deleteRemoteKeys()

    fun getFavoriteMovie() = movieFavoriteDao.getAll()

    fun isMovieInDatabase(id: Int) : Flow<Int> = movieFavoriteDao.isMovieInDatabase(id)

    suspend fun insertFavoriteMovie(movie: MovieFavoriteEntity) = movieFavoriteDao.insertMovies(movie)

    suspend fun deleteFavoriteMovie(movie: MovieFavoriteEntity) : Int = movieFavoriteDao.deleteMovie(movie)




}