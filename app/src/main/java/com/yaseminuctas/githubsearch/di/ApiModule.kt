package com.yaseminuctas.githubsearch.di

import com.yaseminuctas.githubsearch.model.network.AppService
import com.yaseminuctas.githubsearch.model.repositories.AppRepository
import com.yaseminuctas.githubsearch.model.repositories.AppRepositoryImpl
import com.yaseminuctas.githubsearch.model.repositories.AppServiceFactory
import org.koin.dsl.module


val apiModule = module {

    factory<AppRepository> {
        AppRepositoryImpl(
            get()
        )
    }

    factory {
        AppServiceFactory.buildService()
    }

}