package com.lukyanov.chart.domain.mapper

import com.lukyanov.chart.data.model.currency.CurrencyDTO
import com.lukyanov.chart.domain.model.CurrencyModel

fun CurrencyDTO.map(): CurrencyModel {
    return CurrencyModel(
        code = this.symbol.orEmpty(),
        name = this.name.orEmpty()
    )
}

fun List<CurrencyDTO>.map(): List<CurrencyModel>{
    return this.map { it.map() }
}
