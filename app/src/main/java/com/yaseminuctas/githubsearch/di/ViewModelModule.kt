package com.yaseminuctas.githubsearch.di

import com.yaseminuctas.githubsearch.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(get())
    }

}