package com.yaseminuctas.githubsearch.core

import android.app.Application
import com.yaseminuctas.githubsearch.di.apiModule
import com.yaseminuctas.githubsearch.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubSearchApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@GithubSearchApplication)
            modules(listOf(apiModule, viewModelModule))
        }
    }
}

