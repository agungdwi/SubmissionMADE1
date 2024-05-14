package com.example.core.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie order by page asc, popularity desc")
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie")
    suspend fun deleteAll()


}