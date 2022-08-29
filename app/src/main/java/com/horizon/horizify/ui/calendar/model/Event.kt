package com.horizon.horizify.ui.calendar.model

import com.horizon.horizify.ui.calendar.CalendarEventColorEnum

data class EventModel(val hasEvent : Boolean = false, val eventColor: CalendarEventColorEnum = CalendarEventColorEnum.NONE)