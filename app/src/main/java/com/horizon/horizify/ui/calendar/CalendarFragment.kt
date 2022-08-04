package com.horizon.horizify.ui.calendar

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.handleBackPress
import com.horizon.horizify.ui.calendar.item.CalendarItem
import com.horizon.horizify.ui.calendar.viewmodel.CalendarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarFragment : GroupieFragment() {

    private val vm : CalendarViewModel by viewModel()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {
            val calendarItem = CalendarItem(vm)
            add(calendarItem)
        }

        handleBackPress { onBackPressed() }
    }

    private fun onBackPressed() = popBack()
}