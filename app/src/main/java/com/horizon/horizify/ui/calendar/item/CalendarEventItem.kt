package com.horizon.horizify.ui.calendar.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.CalendarEventItemBinding
import com.horizon.horizify.ui.calendar.model.CalendarDayModel
import com.horizon.horizify.utils.BindableItemObserver
import com.xwray.groupie.viewbinding.BindableItem

class CalendarEventItem : BindableItem<CalendarEventItemBinding>() {

    var model by BindableItemObserver(CalendarDayModel())

    override fun bind(viewBinding: CalendarEventItemBinding, position: Int) {
        with(viewBinding) {

        }
    }

    override fun getLayout(): Int = R.layout.calendar_event_item

    override fun initializeViewBinding(view: View): CalendarEventItemBinding = CalendarEventItemBinding.bind(view)
}