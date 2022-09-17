package com.horizon.horizify.ui.calendar

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.handleBackPress
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.calendar.item.CalendarEventBody
import com.horizon.horizify.ui.calendar.item.CalendarItem
import com.horizon.horizify.ui.calendar.viewmodel.CalendarViewModel
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarFragment : GroupieFragment() {

    private val calendarViewModel: CalendarViewModel by viewModel()

    private var eventSection = Section()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {
            val calendarItem = CalendarItem(calendarViewModel)
            calendarViewModel.dateEventList.forEach { event ->
                if (event.events.isNotEmpty()){
                    eventSection.add(CalendarEventBody(event))
                }
            }
            setBody(calendarItem, eventSection)
        }

        handleBackPress { onBackPressed() }
    }

    private fun onBackPressed() = popBack()
}