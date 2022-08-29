package com.horizon.horizify.ui.calendar.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.utils.Constants
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarViewModel : BaseViewModel() {

    var selectedDate: LocalDate? = null
    var dateCounter: Int = 0

    val dateEventList = listOf(
        CalendarDay("2022-08-24", emptyList(), EventColor.BLUE),
        CalendarDay("2022-08-30", emptyList(), EventColor.RED),
        CalendarDay("2022-09-20", emptyList(), EventColor.GREEN),
        CalendarDay("2022-09-05", emptyList(), EventColor.PURPLE)
    )

    fun isSunday(input: LocalDate): Boolean {
//        val dateFormat = SimpleDateFormat("EEEE")
//        val day = dateFormat.format(date).toString()

        val inFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = inFormat.parse(input.toString())
        val outFormat = SimpleDateFormat("EEEE")
        val goal = outFormat.format(date)
        return goal == Constants.SUNDAY
    }

    fun hasEvent( date : String ) : Event {
        val event = dateEventList.find { it.date == date }
        return if (event != null ) Event(true, event.colorIndicator) else Event()
    }
}
data class Event(val hasEvent : Boolean = false, val eventColor: EventColor = EventColor.NONE)

data class CalendarDay(val date:String, val events: List<DayEvent>, val colorIndicator: EventColor)

data class DayEvent( val img: String, val title: String, val description: String)

enum class EventColor {
    NONE,
    BLUE,
    RED,
    GREEN,
    PURPLE
}