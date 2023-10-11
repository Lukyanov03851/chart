package com.lukyanov.chart.data.model.history

enum class Range(val value: String) {
    DAY("1D"), WEEK("1W"), MONTH("1M"), YEAR("1Y"), ALL("ALL");

    companion object {
        fun findRangeByValue(value: String): Range {
            return values().find { it.value == value } ?: DAY
        }
    }
}
