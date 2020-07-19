package com.carwale.covidapp.utils.extensions

import android.content.Context
import com.carwale.covidapp.utils.Constants
import com.carwale.covidapp.utils.shared_prefrence.SharedPref
import com.carwale.covidapp.R
import java.text.SimpleDateFormat
import java.util.*

const val NETWORK_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val READABLE_FORMAT = "MMMM d, yyyy"
const val READABLE_FORMAT_WITH_DATE = "h:mm a, MMMM d, yyyy"
const val LONG_READABLE_FORMAT = "dd/MM/yy h:mm a"
const val SHORT_TIME_FORMAT = "h:mm a"
const val MONTH_YEAR_FORMAT = "MMMM yyyy"
const val DAY_MONTH_FORMAT = "EEE, dd MMM"
const val DATE_MONTH_FORMAT = "d MMMM"
const val SMALL_MONTH_FORMAT = "MMMM YY"
const val SNAKE_FORMAT = "dd/MM/yy"

//fun getFormattedDate(date: Date?, format: String): String =
//    SimpleDateFormat(format, Locale(SharedPref.getInstance().getStringPreference(Constants.SharedPrefTags.SELECTED_LOCALE))).format(date ?: Date())
//
//fun getFormattedDate(date: String, toFormat: String, fromFormat: String = NETWORK_FORMAT) = try {
//    getFormattedDate(SimpleDateFormat(fromFormat, Locale(SharedPref.getInstance().getStringPreference(Constants.SharedPrefTags.SELECTED_LOCALE))).parse(date), toFormat)
//} catch (e: Exception) {
//    ""
//}
//
//fun getFormattedDate(date: Date?): String = getFormattedDate(date, NETWORK_FORMAT)

fun newCustomInstance(i: Int, hrs: Int, min: Int, sec: Int): Calendar {
    val date = Date()
    val d = Calendar.getInstance()
    d.time = Date(date.year, date.month, date.date - i, hrs, min, sec)
    return d
}

fun newInstanceForPreviousMonth(i: Int, hrs: Int, min: Int, sec: Int): Calendar {
    val date = Date()
    val d = Calendar.getInstance()
    d.time = Date(date.year, date.month - i, date.date, hrs, min, sec)
    return d
}

fun getLocale(fromFormat: String): Locale = if (fromFormat == NETWORK_FORMAT) {
    Locale.ENGLISH
} else {
    Locale(
        SharedPref.getInstance().getStringPreference(Constants.SharedPrefTags.SELECTED_LOCALE)
    )
}

fun String.toDateFormat(toFormat: String, fromFormat: String = NETWORK_FORMAT): String {
    return SimpleDateFormat(toFormat, getLocale(fromFormat)).format(
        SimpleDateFormat(
            fromFormat,
            getLocale(fromFormat)
        ).parse(this) ?: Date()
    )
}

fun String.toCalender(format: String = NETWORK_FORMAT): Calendar {
    val cal = Calendar.getInstance()
    cal.time = SimpleDateFormat(format, getLocale(format)).parse(this) ?: Date()
    return cal
}

fun Long.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    return cal
}

fun Long.toDateFormat(format: String = NETWORK_FORMAT): String = this.toCalendar().toFormat(format)

fun Calendar?.beforeOrCurrent(newDate: Calendar): Boolean =
    this != null && (this.before(newDate) || this == newDate)

fun Calendar?.isToday(): Boolean = this != null &&
        this.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

fun Calendar?.isYesterday(): Boolean = this != null &&
        Calendar.getInstance().get(Calendar.DATE) - this.get(Calendar.DATE) == 1

fun Calendar.toFormat(format: String = NETWORK_FORMAT): String =
    SimpleDateFormat(format, getLocale(format)).format(this.time)

fun Calendar.previousMonth(): String {
    this.set(Calendar.MONTH, this.get(Calendar.MONTH) - 1)
    return this.toFormat(MONTH_YEAR_FORMAT)
}

fun Calendar.toDateRange(date: Int, ctx: Context?): String? {
    val currentMonth = this.get(Calendar.MONTH)

    val start = Calendar.getInstance()
    start.set(Calendar.DATE, date)
    val end = Calendar.getInstance()
    end.set(Calendar.DATE, date - 1)

    if (date > this.get(Calendar.DATE)) {
        start.set(Calendar.MONTH, currentMonth - 2)
        end.set(Calendar.MONTH, currentMonth - 1)
    } else {
        start.set(Calendar.MONTH, currentMonth - 1)
        end.set(Calendar.MONTH, currentMonth)
    }
    return ctx?.getString(
        R.string._to_,
        start.toFormat(DATE_MONTH_FORMAT),
        end.toFormat(DATE_MONTH_FORMAT)
    )
}
