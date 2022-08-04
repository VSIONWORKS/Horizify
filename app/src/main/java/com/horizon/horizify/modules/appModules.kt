package com.horizon.horizify.modules

import com.horizon.horizify.ui.calendar.CalendarModule
import com.horizon.horizify.ui.home.HomeModule

val appModules = listOf(
    HomeModule.dependency,
    CalendarModule.dependency
)


class HelloSayer() {
    fun sayHello() = "Hello"
}



