package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val popularity: Double,
    val voteAverage: Double,
    val releaseDate: String,
    var page: Int
) : Parcelable