package com.lukyanov.chart.ui.chart.util

import com.lukyanov.chart.R
import com.lukyanov.chart.domain.model.DateRange

fun DateRange.toTabTitle(): Int {
    return when (this) {
        DateRange.DAY -> R.string.day_range_title
        DateRange.WEEK -> R.string.week_range_title
        DateRange.MONTH -> R.string.month_range_title
        DateRange.YEAR -> R.string.year_range_title
        DateRange.ALL -> R.string.all_range_title
    }
}
