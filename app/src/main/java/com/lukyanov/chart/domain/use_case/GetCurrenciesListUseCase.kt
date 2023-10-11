package com.lukyanov.chart.domain.use_case

import com.lukyanov.chart.data.repository.CurrencyRepository
import com.lukyanov.chart.domain.model.CurrencyModel
import com.lukyanov.chart.domain.mapper.map
import javax.inject.Inject

class GetCurrenciesListUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun invoke(): List<CurrencyModel> {
        return currencyRepository.getCurrenciesList().map()
    }
}
