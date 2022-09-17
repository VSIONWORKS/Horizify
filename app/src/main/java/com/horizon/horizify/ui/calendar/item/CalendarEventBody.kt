package com.horizon.horizify.ui.calendar.item

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.horizon.horizify.R
import com.horizon.horizify.databinding.CalendarEventBodyBinding
import com.horizon.horizify.extensions.toFormattedDate
import com.horizon.horizify.ui.calendar.model.CalendarDayModel
import com.horizon.horizify.utils.Constants.getEventColor
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class CalendarEventBody(val model: CalendarDayModel = CalendarDayModel()) : BindableItem<CalendarEventBodyBinding>() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun bind(viewBinding: CalendarEventBodyBinding, position: Int) {
        with(viewBinding) {
            barLayout.setBackgroundResource(getEventColor(model.colorIndicator))
            rvEvents.apply {
                adapter = groupAdapter.apply {
                    model.events.forEach {
                        add(CalendarEventItem(it))
                    }
                }
                layoutManager = LinearLayoutManager(context)
            }
            val monthString = model.date.toFormattedDate("yyyy-MM-dd", "MMM")
            val monthNumber = model.date.toFormattedDate("yyyy-MM-dd", "dd")
            textMonth.text = monthString
            textDay.text = monthNumber
        }
    }

    override fun getLayout(): Int = R.layout.calendar_event_body

    override fun initializeViewBinding(view: View): CalendarEventBodyBinding = CalendarEventBodyBinding.bind(view)
}