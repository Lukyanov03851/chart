package com.lukyanov.chart.ui.currency.mvi

import com.lukyanov.chart.core.view_model.UiEffect

sealed class CurrencyUiEffect : UiEffect {

    data class OnCurrencyChanged(
        val currencyName: String,
        val currencyCode: String
    ) : CurrencyUiEffect()
}
