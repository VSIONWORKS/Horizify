package com.horizon.horizify.ui.calendar.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.calendar.CalendarEventColorEnum
import com.horizon.horizify.ui.calendar.model.CalendarDayModel
import com.horizon.horizify.ui.calendar.model.DayEventModel
import com.horizon.horizify.ui.calendar.model.EventModel
import com.horizon.horizify.utils.Constants
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarViewModel : BaseViewModel() {

    var selectedDate: LocalDate? = null

    val testEvent = DayEventModel(
        img = "",
        title = "Test Event",
        description = "",
        time = "01:00"
    )

    val bahaySimulaEvent = DayEventModel(
        img = "",
        title = "Bahay Simula",
        description = "",
        time = "03:00"
    )

    val bahaySimulaEvent2 = DayEventModel(
        img = "",
        title = "Bahay Simula Part2",
        description = "",
        time = "12:00"
    )

    val dateEventList = listOf(
        CalendarDayModel("2022-08-24", emptyList(), CalendarEventColorEnum.BLUE),
        CalendarDayModel("2022-08-30", emptyList(), CalendarEventColorEnum.RED),
        CalendarDayModel("2022-09-20", listOf(testEvent), CalendarEventColorEnum.GREEN),
        CalendarDayModel("2022-09-18", listOf(bahaySimulaEvent, bahaySimulaEvent2), CalendarEventColorEnum.RED),
        CalendarDayModel("2022-09-05", emptyList(), CalendarEventColorEnum.PURPLE)
    )

    fun isSunday(input: LocalDate): Boolean {
        val inFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = inFormat.parse(input.toString())
        val outFormat = SimpleDateFormat("EEEE")
        val goal = outFormat.format(date)
        return goal == Constants.SUNDAY
    }

    fun hasEvent(date: String): EventModel {
        val event = dateEventList.find { it.date == date }
        return if (event != null) EventModel(true, event.colorIndicator) else EventModel()
    }
    fun getEvents(date: String): CalendarDayModel? {
        return dateEventList.find { it.date == date }
    }
}