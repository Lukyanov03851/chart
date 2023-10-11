package com.lukyanov.chart.core.util

object FormatCurrency {

    private const val PATTERN_AMOUNT = "0.00"

    fun formatFiatCurrency(value: Double, currency: String): String {
        return "${format(value)} $currency"
    }

    fun format(value: Double): String {
        return Format.getFormatCurrency(PATTERN_AMOUNT).format(value)
    }
}