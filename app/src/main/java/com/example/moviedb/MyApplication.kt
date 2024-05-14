package com.example.moviedb

import android.app.Application
import com.example.core.di.databaseModule
import com.example.core.di.networkModule
import com.example.core.di.remoteModule
import com.example.moviedb.di.useCaseModule
import com.example.moviedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    useCaseModule,
                    remoteModule,
                    viewModelModule
                )
            )
        }
    }
}