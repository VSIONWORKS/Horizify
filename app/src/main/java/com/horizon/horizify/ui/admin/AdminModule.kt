package com.horizon.horizify.ui.admin

import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AdminModule {
    val dependency = module {
        viewModel { AdminViewModel() }
    }
}