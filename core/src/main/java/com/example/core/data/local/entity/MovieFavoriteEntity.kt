package com.example.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movieFavorite")
data class MovieFavoriteEntity (

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "posterPath")
    var posterPath: String,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String,

    @ColumnInfo(name= "popularity")
    var popularity: Double,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String,
)