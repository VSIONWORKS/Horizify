package com.horizon.horizify.ui.calendar

import android.os.Bundle
import android.view.View
import com.horizon.horizify.Extensions.handleBackPress
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.ui.calendar.item.CalendarItem

class CalendarFragment : GroupieFragment() {
    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {
            val calendarItem = CalendarItem()
            add(calendarItem)
        }

        handleBackPress { onBackPressed() }
    }

    private fun onBackPressed() = popBack()
}