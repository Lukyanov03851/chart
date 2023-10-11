package com.lukyanov.chart.data.repository

import com.lukyanov.chart.data.model.history.HistoryDTO
import com.lukyanov.chart.data.model.history.Range
import com.lukyanov.chart.data.source.history.HistoryRemoteDataSource
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val remoteDataSource: HistoryRemoteDataSource
) {
    suspend fun getPriceHistory(
        currency: String,
        dateRange: Range,
    ): HistoryDTO {
        return remoteDataSource.getPriceHistory(
            currency = currency,
            dateRange = dateRange,
        )
    }
}
