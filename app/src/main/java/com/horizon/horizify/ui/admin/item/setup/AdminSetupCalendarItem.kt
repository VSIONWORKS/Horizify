package com.horizon.horizify.ui.admin.item.setup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.horizon.horizify.R
import com.horizon.horizify.common.ui.modal.GenericModal
import com.horizon.horizify.databinding.AdminSetupCalendarColorPickerModalBinding
import com.horizon.horizify.databinding.AdminSetupCalendarItemBinding
import com.horizon.horizify.extensions.toFormattedDateString
import com.horizon.horizify.extensions.toSafeFormattedDateString
import com.horizon.horizify.ui.calendar.CalendarEventColorEnum
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat
import java.util.*


class AdminSetupCalendarItem : BindableItem<AdminSetupCalendarItemBinding>() {

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    var eventColor = CalendarEventColorEnum.BLUE

    override fun bind(viewBinding: AdminSetupCalendarItemBinding, position: Int) {
        viewBinding.apply {
            initDateTimeSetup()
            btnSetDate.setOnClickListener { showDatePicker() }
            btnSetTime.setOnClickListener { showTimePicker() }
            btnSetEventColor.setOnClickListener { showColorPicker() }
        }
    }

    override fun getLayout(): Int = R.layout.admin_setup_calendar_item

    override fun initializeViewBinding(view: View): AdminSetupCalendarItemBinding = AdminSetupCalendarItemBinding.bind(view)

    private fun AdminSetupCalendarItemBinding.initDateTimeSetup() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)

        val date = "$year-$month-$day"
        textDate.text = getFormattedDate(date, "yyyy-MM-dd", "MMMM dd, yyyy")

        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        textTime.text = formatTime(hour, minute)
    }

    private fun AdminSetupCalendarItemBinding.showDatePicker() {

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myDay = dayOfMonth
            myYear = year
            myMonth = month + 1
            val date = "$myYear-$myMonth-$myDay"
            textDate.text = getFormattedDate(date, "yyyy-MM-dd", "MMMM dd, yyyy")
        }

        val datePickerDialog = DatePickerDialog(root.context, listener, year, month, day)
        datePickerDialog.show()
    }

    private fun AdminSetupCalendarItemBinding.showTimePicker() {

        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            textTime.text = formatTime(hourOfDay, minute)
        }

        val timePickerDialog = TimePickerDialog(
            root.context, listener, hour, minute,
            DateFormat.is24HourFormat(root.context)
        )
        timePickerDialog.show()
    }

    private fun formatTime(hourOfDay: Int, minute: Int): String {
        val originalFormat = SimpleDateFormat("HH:mm")
        val targetFormatTo = SimpleDateFormat("hh:mm a")
        val timeToFormat = originalFormat.parse("$hourOfDay:$minute")
        return targetFormatTo.format(timeToFormat)
    }

    private fun AdminSetupCalendarItemBinding.showColorPicker() {
        with(root) {
            val dialogBinding = AdminSetupCalendarColorPickerModalBinding.inflate(LayoutInflater.from(context))
            val dialog = GenericModal(context, dialogBinding.root)
            with(dialog) {
                dialogBinding.apply {
                    rdBlue.setOnCheckedChangeListener { _, isBlue ->
                        if (isBlue) {
                            eventColor = CalendarEventColorEnum.BLUE
                            imgEventColor.imageTintList = ContextCompat.getColorStateList(context, R.color.calendar_event_blue)
                        }
                        dismiss()
                    }
                    rdRed.setOnCheckedChangeListener { _, isRed ->
                        if (isRed) {
                            eventColor = CalendarEventColorEnum.RED
                            imgEventColor.imageTintList = ContextCompat.getColorStateList(context, R.color.calendar_event_red)
                        }
                        dismiss()
                    }
                    rdGreen.setOnCheckedChangeListener { _, isGreen ->
                        if (isGreen) {
                            eventColor = CalendarEventColorEnum.GREEN
                            imgEventColor.imageTintList = ContextCompat.getColorStateList(context, R.color.calendar_event_green)
                        }
                        dismiss()
                    }
                    rdPurple.setOnCheckedChangeListener { _, isPurple ->
                        if (isPurple) {
                            eventColor = CalendarEventColorEnum.PURPLE
                            imgEventColor.imageTintList = ContextCompat.getColorStateList(context, R.color.calendar_event_purple)
                        }
                        dismiss()
                    }

                    when (eventColor) {
                        CalendarEventColorEnum.RED -> rdRed.isChecked = true
                        CalendarEventColorEnum.GREEN -> rdGreen.isChecked = true
                        CalendarEventColorEnum.PURPLE -> rdPurple.isChecked = true
                        else -> rdBlue.isChecked = true
                    }
                }

                show()
            }
        }
    }

    private fun getFormattedDate(date: String, inPattern: String, outPattern: String): String {
        val dateToFormat = date.toSafeFormattedDateString()
        return dateToFormat.toFormattedDateString(inPattern, outPattern)
    }
}