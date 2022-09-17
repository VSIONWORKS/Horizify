package com.horizon.horizify.ui.calendar.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.CalendarEventItemBinding
import com.horizon.horizify.ui.calendar.model.DayEventModel
import com.xwray.groupie.viewbinding.BindableItem

class CalendarEventItem(val model: DayEventModel = DayEventModel()) : BindableItem<CalendarEventItemBinding>() {

    override fun bind(viewBinding: CalendarEventItemBinding, position: Int) {
        with(viewBinding) {
            eventTitle.text = model.title
            eventTime.text = model.time
        }
    }

    override fun getLayout(): Int = R.layout.calendar_event_item

    override fun initializeViewBinding(view: View): CalendarEventItemBinding = CalendarEventItemBinding.bind(view)
}