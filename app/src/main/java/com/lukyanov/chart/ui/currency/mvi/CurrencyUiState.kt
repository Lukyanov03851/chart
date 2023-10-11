package com.lukyanov.chart.ui.currency.mvi

import com.lukyanov.chart.core.ui.components.radio.RadioButtonModel
import com.lukyanov.chart.core.view_model.UiState
import com.lukyanov.chart.domain.model.CurrencyModel

data class CurrencyUiState(
    val isLoading: Boolean = false,
    val currenciesList: List<RadioButtonModel<CurrencyModel>> = emptyList(),
) : UiState
