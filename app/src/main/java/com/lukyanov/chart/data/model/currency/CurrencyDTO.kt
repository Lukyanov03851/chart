package com.lukyanov.chart.data.model.currency

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDTO(
    @SerialName(value = "name") val name: String? = null,
    @SerialName(value = "symbol") val symbol: String? = null,
)