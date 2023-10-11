package com.lukyanov.chart.core.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Format {

    private const val CURRENCY_GROUPING_SIZE = 3
    private const val CURRENCY_GROUPING_SEPARATOR = ' '
    private const val CURRENCY_DECIMAL_SEPARATOR = ','

    fun getFormatCurrency(pattern: String): DecimalFormat {
        val decimalFormat = DecimalFormat(pattern, DecimalFormatSymbols(Locale.getDefault()))
        val decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.getDefault())
        decimalFormatSymbols.decimalSeparator = CURRENCY_DECIMAL_SEPARATOR
        decimalFormat.roundingMode = RoundingMode.DOWN
        decimalFormat.decimalFormatSymbols = decimalFormatSymbols
        decimalFormat.isGroupingUsed = true
        decimalFormat.decimalFormatSymbols.groupingSeparator = CURRENCY_GROUPING_SEPARATOR
        decimalFormat.groupingSize = CURRENCY_GROUPING_SIZE
        return decimalFormat
    }

}