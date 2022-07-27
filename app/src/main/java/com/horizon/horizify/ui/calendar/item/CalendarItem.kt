package com.horizon.horizify.ui.calendar.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.FragmentCalendarBinding
import com.xwray.groupie.viewbinding.BindableItem

class CalendarItem : BindableItem<FragmentCalendarBinding>() {
    override fun bind(viewBinding: FragmentCalendarBinding, position: Int) {
        with(viewBinding){
            calendarTest.text = "This is my calendar"
        }
    }

    override fun getLayout(): Int  = R.layout.fragment_calendar

    override fun initializeViewBinding(view: View): FragmentCalendarBinding = FragmentCalendarBinding.bind(view)
}