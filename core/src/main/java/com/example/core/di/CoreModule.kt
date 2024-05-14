package com.example.core.di

import androidx.room.Room
import com.example.core.data.MovieRepository
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.room.DatabaseTransactionHelper
import com.example.core.data.local.room.MovieDatabase
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.network.ApiService
import com.example.core.domain.repository.IMovieRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().build()
    }

    // Provide MovieDao as a singleton
    single { get<MovieDatabase>().movieDao() }
//
    // Provide RemoteKeysDao as a singleton
    single { get<MovieDatabase>().remoteKeysDao() }

    single { get<MovieDatabase>().movieFavoriteDao() }

    single { DatabaseTransactionHelper(get()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZjI5YjNlMmIzZTZmYTg1YjAwYTFjODM4ZjU3MmMzMCIsInN1YiI6IjY2MzczYTFlOWE2NGMxMDEyYzNmMjNjOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vPvhq436kIoYmA5JI9gbkwkgwmjeOnZkpsKJ9DzUmLw")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val remoteModule = module{
    single { LocalDataSource(get(),get(), get())}
    single { RemoteDataSource(get()) }
    single<IMovieRepository>{ MovieRepository(get(),get(),get(),get()) }
}

