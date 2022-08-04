package com.horizon.horizify.ui.calendar

import com.horizon.horizify.ui.calendar.viewmodel.CalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object CalendarModule {
    val dependency = module {
        viewModel { CalendarViewModel() }
    }
}