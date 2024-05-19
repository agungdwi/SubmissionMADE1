package com.example.core.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.entity.MovieEntity
import com.example.core.data.local.entity.MovieFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieFavoriteDao {
    @Query("SELECT * FROM movieFavorite")
    fun getAll(): Flow<List<MovieFavoriteEntity>>

    @Query("SELECT COUNT(*) FROM movieFavorite WHERE id = :movieId")
    fun isMovieInDatabase(movieId: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: MovieFavoriteEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieFavoriteEntity) : Int

}