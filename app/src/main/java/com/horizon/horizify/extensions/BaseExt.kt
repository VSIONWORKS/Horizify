package com.horizon.horizify.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun <T : View> T.isCollapseExpand(isExpand: Boolean) {
    val params = layoutParams
    if (isExpand) {
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
    } else {
        params.height = 0
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
    }
    layoutParams = params
}

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

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

fun ImageView.loadFitCenter(
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
        .fitCenter()
        .apply(requestOptions)
        .into(this)
}

fun String.containsIgnoreCase(str: String): Boolean = this.contains(str, true)

fun String.toFormattedDate(inPattern: String = "yyyy-MMMM-d", outPattern: String = "yyyy-MM-dd"): String {
    val fmt: DateFormat = SimpleDateFormat(inPattern, Locale.US)
    val fmtOut: DateFormat = SimpleDateFormat(outPattern, Locale.US)
    val dateInstance = fmt.parse(this) ?: return this
    return fmtOut.format(dateInstance)
}

fun String.trimAllSpaces(): String = this.replace(" ", "")