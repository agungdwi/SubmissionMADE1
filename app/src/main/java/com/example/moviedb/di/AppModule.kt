package com.example.moviedb.di

import com.example.core.domain.usecase.MovieInteractor
import com.example.core.domain.usecase.MovieUseCase
import com.example.moviedb.presentation.detail.DetailViewModel
import com.example.moviedb.presentation.home.HomeViewModel
import com.example.moviedb.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DetailViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }



}