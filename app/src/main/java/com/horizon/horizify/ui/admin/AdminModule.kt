package com.horizon.horizify.ui.admin

import com.horizon.horizify.ui.admin.repository.AdminRepository
import com.horizon.horizify.ui.admin.repository.AdminRepositoryImpl
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AdminModule {
    val dependency = module {
        factory<AdminRepository> { AdminRepositoryImpl(get()) }
        viewModel { AdminViewModel(androidContext(), get()) }
    }

    const val SETUP_MODE = "setup"
    const val SETUP_PRIMMARY_BANNER = "setup_primary_banner"
    const val SETUP_BANNER = "setup_banner"
    const val SETUP_NETWORK = "setup_network"
}