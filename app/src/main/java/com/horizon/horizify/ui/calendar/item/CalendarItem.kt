package com.horizon.horizify.ui.calendar.item

import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.horizon.horizify.R
import com.horizon.horizify.databinding.CalendarDayBinding
import com.horizon.horizify.databinding.CalendarItemBinding
import com.horizon.horizify.extensions.daysOfWeekFromLocale
import com.horizon.horizify.extensions.setTextColorRes
import com.horizon.horizify.ui.calendar.viewmodel.CalendarViewModel
import com.horizon.horizify.utils.Constants.getEventColor
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.yearMonth
import com.xwray.groupie.viewbinding.BindableItem
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class CalendarItem(val viewModel: CalendarViewModel) : BindableItem<CalendarItemBinding>() {

    private val today = LocalDate.now()
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun bind(viewBinding: CalendarItemBinding, position: Int) {
        with(viewBinding) {
            val daysOfWeek = daysOfWeekFromLocale()
            val currentMonth = YearMonth.now()
            val startMonth = currentMonth.minusMonths(10)
            val endMonth = currentMonth.plusMonths(10)

            legendLayout.root.children.forEachIndexed { index, view ->
                (view as TextView).apply {
                    text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(Locale.ENGLISH)
                    if (index == 0) setTextColorRes(R.color.outbound_legend)
                    else setTextColorRes(R.color.semi_white)
                }
            }
            exOneCalendar.setup(startMonth, endMonth, daysOfWeek.first())
            exOneCalendar.scrollToMonth(currentMonth)

            exOneCalendar.dayBinder = object : DayBinder<DayViewContainer> {
                override fun bind(container: DayViewContainer, day: CalendarDay) {
                    container.day = day
                    val dateStr = day.date
                    val event = viewModel.hasEvent(day.date.toString())

                    val textView = container.textView
                    val eventLine = container.eventLine
                    textView.text = day.date.dayOfMonth.toString()

                    if (day.owner == DayOwner.THIS_MONTH) {
                        when (dateStr) {
                            viewModel.selectedDate -> {
                                textView.setTextColorRes(R.color.semi_white)
                                textView.setBackgroundResource(R.drawable.calendar_selected_day)
                                eventLine.isVisible = false
                            }
                            today -> {
                                textView.setTextColorRes(R.color.semi_white)
                                textView.setBackgroundResource(R.drawable.calendar_current_day)
                                eventLine.isVisible = false
                            }
                            else -> {
                                if (viewModel.isSunday(dateStr)) textView.setTextColorRes(R.color.sunday)
                                else textView.setTextColorRes(R.color.blue_toolbar)

                                eventLine.isVisible = event.hasEvent
                                eventLine.setBackgroundResource(getEventColor(event.eventColor))
                                textView.background = null
                            }
                        }
                    } else {
                        textView.setTextColorRes(R.color.outbound_day)
                        textView.background = null
                    }
                }

                override fun create(view: View): DayViewContainer = DayViewContainer(viewModel, viewBinding, view)
            }

            exOneCalendar.monthScrollListener = {
                if (exOneCalendar.maxRowCount == 6) {
                    exOneYearText.text = it.yearMonth.year.toString()
                    exOneMonthText.text = monthTitleFormatter.format(it.yearMonth)
                } else {
                    // In week mode, we show the header a bit differently.
                    // We show indices with dates from different months since
                    // dates overflow and cells in one index can belong to different
                    // months/years.
                    val firstDate = it.weekDays.first().first().date
                    val lastDate = it.weekDays.last().last().date
                    if (firstDate.yearMonth == lastDate.yearMonth) {
                        exOneYearText.text = firstDate.yearMonth.year.toString()
                        exOneMonthText.text = monthTitleFormatter.format(firstDate)
                    } else {
                        exOneMonthText.text =
                            "${monthTitleFormatter.format(firstDate)} - ${monthTitleFormatter.format(lastDate)}"
                        if (firstDate.year == lastDate.year) {
                            exOneYearText.text = firstDate.yearMonth.year.toString()
                        } else {
                            exOneYearText.text = "${firstDate.yearMonth.year} - ${lastDate.yearMonth.year}"
                        }
                    }
                }
            }

            weekModeCheckBox.setOnCheckedChangeListener { _, monthToWeek ->
                val firstDate = exOneCalendar.findFirstVisibleDay()?.date ?: return@setOnCheckedChangeListener
                val lastDate = exOneCalendar.findLastVisibleDay()?.date ?: return@setOnCheckedChangeListener

                val oneWeekHeight = exOneCalendar.daySize.height
                val oneMonthHeight = oneWeekHeight * 6

                val oldHeight = if (monthToWeek) oneMonthHeight else oneWeekHeight
                val newHeight = if (monthToWeek) oneWeekHeight else oneMonthHeight

                // Animate calendar height changes.
                val animator = ValueAnimator.ofInt(oldHeight, newHeight)
                animator.addUpdateListener { animator ->
                    exOneCalendar.updateLayoutParams {
                        height = animator.animatedValue as Int
                    }
                }

                // When changing from month to week mode, we change the calendar's
                // config at the end of the animation(doOnEnd) but when changing
                // from week to month mode, we change the calendar's config at
                // the start of the animation(doOnStart). This is so that the change
                // in height is visible. You can do this whichever way you prefer.

                animator.doOnStart {
                    if (!monthToWeek) {
                        exOneCalendar.updateMonthConfiguration(
                            inDateStyle = InDateStyle.ALL_MONTHS,
                            maxRowCount = 6,
                            hasBoundaries = true
                        )
                    }
                }
                animator.doOnEnd {
                    if (monthToWeek) {
                        exOneCalendar.updateMonthConfiguration(
                            inDateStyle = InDateStyle.FIRST_MONTH,
                            maxRowCount = 1,
                            hasBoundaries = false
                        )
                    }

                    if (monthToWeek) {
                        // We want the first visible day to remain
                        // visible when we change to week mode.
                        exOneCalendar.scrollToDate(firstDate)
                    } else {
                        // When changing to month mode, we choose current
                        // month if it is the only one in the current frame.
                        // if we have multiple months in one frame, we prefer
                        // the second one unless it's an outDate in the last index.
                        if (firstDate.yearMonth == lastDate.yearMonth) {
                            exOneCalendar.scrollToMonth(firstDate.yearMonth)
                        } else {
                            // We compare the next with the last month on the calendar so we don't go over.
                            exOneCalendar.scrollToMonth(minOf(firstDate.yearMonth.next, endMonth))
                        }
                    }
                }
                animator.duration = 250
                animator.start()
            }
        }
    }

    override fun getLayout(): Int = R.layout.calendar_item

    override fun initializeViewBinding(view: View): CalendarItemBinding = CalendarItemBinding.bind(view)
}

class DayViewContainer(viewModel: CalendarViewModel, binding: CalendarItemBinding, view: View) : ViewContainer(view) {
    // Will be set when this container is bound. See the dayBinder.
    lateinit var day: CalendarDay
    val textView = CalendarDayBinding.bind(view).exOneDayText
    val eventLine = CalendarDayBinding.bind(view).vEvent

    init {
        view.setOnClickListener {
            with(binding) {
                if (day.owner == DayOwner.THIS_MONTH) {
                    if (viewModel.selectedDate == day.date) {
                        viewModel.selectedDate = null
                        exOneCalendar.notifyDayChanged(day)
                    } else {
                        val oldDate = viewModel.selectedDate
                        viewModel.selectedDate = day.date
                        exOneCalendar.notifyDateChanged(day.date)
                        oldDate?.let { exOneCalendar.notifyDateChanged(oldDate) }
                    }
                }
            }
        }
    }
}
