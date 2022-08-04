package com.horizon.horizify.ui.calendar.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.utils.Constants
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarViewModel : BaseViewModel() {

    var selectedDate: LocalDate? = null
    var dateCounter: Int = 0

    fun isSunday(input: LocalDate): Boolean {
//        val dateFormat = SimpleDateFormat("EEEE")
//        val day = dateFormat.format(date).toString()

        val inFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = inFormat.parse(input.toString())
        val outFormat = SimpleDateFormat("EEEE")
        val goal = outFormat.format(date)
        return goal == Constants.SUNDAY
    }
}