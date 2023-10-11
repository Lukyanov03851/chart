package com.lukyanov.chart.ui.currency.mvi

import com.lukyanov.chart.domain.model.CurrencyModel

interface CurrencyUiIntent {
    fun changeCurrency(currency: CurrencyModel)
}

class DefaultCurrencyUiIntent: CurrencyUiIntent {
    override fun changeCurrency(currency: CurrencyModel) {
        /*ignore*/
    }
}
