package com.lukyanov.chart.core.util

import java.text.SimpleDateFormat
import java.util.*

object FormatDate {
    const val DAY_MONTH_YEAR_FORMAT = "dd MMM yy"
    const val DAY_MONTH_YEAR_TIME_FORMAT = "dd MMM yy HH:mm"

    fun formatDate(date: Long, dateFormat: String): String {
        return try {
            val format = SimpleDateFormat(dateFormat, Locale.forLanguageTag("ua"))
            format.format(Date(date))
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
