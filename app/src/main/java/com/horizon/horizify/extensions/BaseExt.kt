package com.horizon.horizify.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}
internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

/**
 * load image url using default argb_8888
 */
fun ImageView.load(
    url: String,
    requestOptions: RequestOptions = RequestOptions()
        .format(DecodeFormat.PREFER_ARGB_8888),
    placeHolder: Int = -1,
    errorHolder: Int = -1
) {
    Glide.with(context)
        .load(url)
        .apply {
            if (placeHolder >= 0) {
                placeholder(placeHolder)
            }
            if (errorHolder >= 0) {
                error(errorHolder)
            }
        }
        .apply(requestOptions)
        .into(this)
}