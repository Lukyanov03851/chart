package com.lukyanov.chart.data.source.currency

import com.lukyanov.chart.data.model.currency.CurrencyCode
import com.lukyanov.chart.data.model.currency.CurrencyDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor() {

    suspend fun getCurrenciesList(): List<CurrencyDTO> {
        return MockCurrencies.getCurrenciesList()
    }
}

object MockCurrencies {
    suspend fun getCurrenciesList(): List<CurrencyDTO> = withContext(Dispatchers.IO) {
        listOf(
            CurrencyDTO(
                name = "Долар США",
                symbol = CurrencyCode.USD.name
            ),
            CurrencyDTO(
                name = "Євро",
                symbol = CurrencyCode.EUR.name
            ),
            CurrencyDTO(
                name = "Англійський фунт",
                symbol = CurrencyCode.GBP.name
            )
        )
    }
}
