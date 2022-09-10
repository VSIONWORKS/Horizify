package com.horizon.horizify.ui.admin

import com.horizon.horizify.ui.admin.repository.AdminRepository
import com.horizon.horizify.ui.admin.repository.AdminRepositoryImpl
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AdminModule {
    val dependency = module {
        factory<AdminRepository> { AdminRepositoryImpl(get(), get()) }
        viewModel { AdminViewModel(androidContext(), get()) }
    }

    const val SETUP_BANNER_MODEL = "banner_model"
    const val SETUP_NETWORK = "setup_network"
}