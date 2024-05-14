package com.example.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.local.entity.MovieEntity
import com.example.core.data.local.entity.MovieFavoriteEntity
import com.example.core.data.local.entity.RemoteKeys


@Database(entities = [MovieEntity::class,  RemoteKeys::class, MovieFavoriteEntity::class], version = 11, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
        abstract fun movieDao(): MovieDao
        abstract fun remoteKeysDao(): RemoteKeysDao
        abstract fun movieFavoriteDao(): MovieFavoriteDao
}