package com.lukyanov.chart.data.model.history

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryDTO(
    @SerialName(value = "lowPrice") val lowPrice: Double? = 0.0,
    @SerialName(value = "maxPrice") val maxPrice: Double? = 0.0,
    @SerialName(value = "averagePrice") val averagePrice: Double? = 0.0,
    @SerialName(value = "historicalData") val historicalData: List<HistoryDataDTO>? = emptyList(),
)
