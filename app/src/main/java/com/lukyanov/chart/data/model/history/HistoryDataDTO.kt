package com.lukyanov.chart.data.model.history

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryDataDTO(
    @SerialName(value = "timestamp") val timestamp: Long? = 0,
    @SerialName(value = "price") val price: Double? = 0.0,
)