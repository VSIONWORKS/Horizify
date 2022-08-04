package com.horizon.horizify.modules

import com.horizon.horizify.ui.calendar.CalendarModule
import com.horizon.horizify.ui.home.HomeModule
import com.horizon.horizify.ui.podcast.PodcastModule

val appModules = listOf(
    HomeModule.dependency,
    CalendarModule.dependency,
    PodcastModule.dependency
)


class HelloSayer() {
    fun sayHello() = "Hello"
}



