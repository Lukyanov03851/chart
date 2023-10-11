package com.lukyanov.chart.domain.mapper

import com.lukyanov.chart.data.model.history.HistoryDTO
import com.lukyanov.chart.data.model.history.HistoryDataDTO
import com.lukyanov.chart.data.model.history.Range
import com.lukyanov.chart.domain.model.DateRange
import com.lukyanov.chart.domain.model.History
import com.lukyanov.chart.domain.model.HistoryData

fun HistoryDTO.mapToModel(): History {
    return History(
        lowPrice = this.lowPrice ?: 0.0,
        maxPrice = this.maxPrice ?: 0.0,
        averagePrice = this.averagePrice ?: 0.0,
        historicalData = this.historicalData?.mapToModel().orEmpty(),
    )
}

fun HistoryDataDTO.mapToModel(): HistoryData {
    return HistoryData(
        date = this.timestamp ?: 0,
        price = this.price ?: 0.0
    )
}

fun List<HistoryDataDTO>.mapToModel(): List<HistoryData> {
    return map { it.mapToModel() }
}

fun DateRange.mapToDateRange(): Range {
    return Range.findRangeByValue(this.value)
}