package com.lukyanov.chart.domain.use_case

import com.lukyanov.chart.data.repository.HistoryRepository
import com.lukyanov.chart.domain.mapper.mapToDateRange
import com.lukyanov.chart.domain.mapper.mapToModel
import com.lukyanov.chart.domain.model.DateRange
import com.lukyanov.chart.domain.model.History
import javax.inject.Inject

class GetPriceHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend fun invoke(
        currency: String,
        range: DateRange,
    ): History {
        return historyRepository.getPriceHistory(
            currency = currency,
            dateRange = range.mapToDateRange()
        ).mapToModel()
    }
}
