package com.horizon.horizify.ui.home

import com.horizon.horizify.ui.home.item.HomeHeaderItem
import com.horizon.horizify.ui.home.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeModule {
    val dependency = module {
        factory { HomeHeaderItem(it[0]) }
        viewModel { HomeViewModel(androidContext()) }
    }
}