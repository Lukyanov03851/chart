package com.lukyanov.chart.ui.chart.mvi

import com.lukyanov.chart.core.view_model.UiState
import com.lukyanov.chart.domain.model.DateRange
import com.lukyanov.chart.domain.model.History
import com.lukyanov.chart.domain.model.HistoryData

data class ChartUiState(
    val isLoading: Boolean = false,
    val currencyName: String = "",
    val currencyCode: String = "",
    val historyData: List<HistoryData> = emptyList(),
    val visualHistoryData: List<Double> = emptyList(),
    val minPrice: Double = 0.0,
    val maxPrice: Double = 0.0,
    val averagePrice: Double = 0.0,
    val isChartSelectionActive: Boolean = false,
    val selectedPrice: Double = 0.0,
    val currentPrice: Double = 0.0,
    val date: Long = System.currentTimeMillis(),
    val selectedTab: Int = 0,
    val historyCache: MutableMap<DateRange, History> = mutableMapOf(),
    val tabs: List<DateRange> = DateRange.values().toList()
) : UiState
