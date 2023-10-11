package com.lukyanov.chart.data.repository

import com.lukyanov.chart.data.model.currency.CurrencyDTO
import com.lukyanov.chart.data.source.currency.CurrencyRemoteDataSource
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val remoteSource: CurrencyRemoteDataSource,
) {

    suspend fun getCurrenciesList(): List<CurrencyDTO> {
        return remoteSource.getCurrenciesList()
    }
}