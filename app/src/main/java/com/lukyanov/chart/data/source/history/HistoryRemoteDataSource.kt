package com.lukyanov.chart.data.source.history

import com.lukyanov.chart.data.model.currency.CurrencyCode
import com.lukyanov.chart.data.model.history.HistoryDTO
import com.lukyanov.chart.data.model.history.HistoryDataDTO
import com.lukyanov.chart.data.model.history.Range
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class HistoryRemoteDataSource @Inject constructor() {

    suspend fun getPriceHistory(
        currency: String,
        dateRange: Range,
    ): HistoryDTO {
        return MockData.getMockData(
            currency = currency,
            dateRange = dateRange,
        )
    }
}

object MockData {
    private const val HOUR = 1000L * 60 * 60

    suspend fun getMockData(
        currency: String,
        dateRange: Range,
    ): HistoryDTO = withContext(Dispatchers.IO) {
        delay(1000)

        val historicalData = getMockHistoryData(
            currency = currency,
            dateRange = dateRange
        )

        val minPrice = historicalData.minBy { (it.price ?: 0.0) }.price ?: 1.0
        val maxPrice = historicalData.maxBy { (it.price ?: 0.0) }.price ?: 100.0

        HistoryDTO(
            lowPrice = minPrice,
            maxPrice = maxPrice,
            averagePrice = (minPrice + maxPrice) / 2,
            historicalData = historicalData
        )
    }

    private fun getMockHistoryData(
        currency: String,
        dateRange: Range,
    ): List<HistoryDataDTO> {
        var minPrice = when (currency) {
            CurrencyCode.USD.name -> 36.5
            CurrencyCode.EUR.name -> 38.5
            else -> 43.1
        }

        val maxPrice = when (currency) {
            CurrencyCode.USD.name -> 38.8
            CurrencyCode.EUR.name -> 41.2
            else -> 45.4
        }

        val historyData = mutableListOf<HistoryDataDTO>()

        val startCalendar = Calendar.getInstance()
        var dateStep = 0L
        val endCalendar = Calendar.getInstance()

        when (dateRange) {
            Range.DAY -> {
                startCalendar.add(Calendar.HOUR, -24)
                dateStep = HOUR
            }

            Range.WEEK -> {
                startCalendar.add(Calendar.DAY_OF_WEEK, -7)
                dateStep = 12 * HOUR
            }

            Range.MONTH -> {
                startCalendar.set(Calendar.DAY_OF_MONTH, -30)
                dateStep = 24 * HOUR
            }

            Range.YEAR -> {
                startCalendar.add(Calendar.YEAR, -1)
                dateStep = 24 * 7 * HOUR
            }

            Range.ALL -> {
                startCalendar.set(Calendar.YEAR, 1996)
                dateStep = 24 * 90 * HOUR
                minPrice = when (currency) {
                    CurrencyCode.USD.name -> 2.5
                    CurrencyCode.EUR.name -> 3.0
                    else -> 4.1
                }
            }
        }

        val dateInterval = endCalendar.timeInMillis - startCalendar.timeInMillis
        val points = (dateInterval / dateStep).toInt()

        var date = startCalendar.timeInMillis


        for (i in 0..points) {
            historyData.add(
                HistoryDataDTO(
                    timestamp = date,
                    price = Random.nextDouble(minPrice, maxPrice)
                )
            )
            date += dateStep
        }

        return historyData
    }
}
