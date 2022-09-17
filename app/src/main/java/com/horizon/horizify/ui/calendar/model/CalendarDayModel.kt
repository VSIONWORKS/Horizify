package com.horizon.horizify.ui.calendar.model

import com.horizon.horizify.ui.calendar.CalendarEventColorEnum

data class CalendarDayModel(
    val date: String = "",
    val events: List<DayEventModel> = emptyList(),
    val colorIndicator: CalendarEventColorEnum = CalendarEventColorEnum.NONE
)