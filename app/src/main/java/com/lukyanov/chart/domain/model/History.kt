package com.lukyanov.chart.domain.model

data class History(
    val lowPrice: Double,
    val maxPrice: Double,
    val averagePrice: Double,
    val historicalData: List<HistoryData>,
)
