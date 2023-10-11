package com.lukyanov.chart.ui.currency

import com.lukyanov.chart.core.ui.components.radio.RadioButtonModel
import com.lukyanov.chart.core.view_model.BaseViewModel
import com.lukyanov.chart.domain.model.CurrencyModel
import com.lukyanov.chart.domain.use_case.GetCurrenciesListUseCase
import com.lukyanov.chart.ui.currency.mvi.CurrencyUiEffect
import com.lukyanov.chart.ui.currency.mvi.CurrencyUiIntent
import com.lukyanov.chart.ui.currency.mvi.CurrencyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrenciesListUseCase: GetCurrenciesListUseCase,
) : BaseViewModel<CurrencyUiState, CurrencyUiEffect>(CurrencyUiState()),
    CurrencyUiIntent {

    init {
        viewModelScope.launch {
            publishState { copy(isLoading = true) }
            val currenciesList = getCurrenciesListUseCase.invoke()
            delay(1500)

            publishState {
                copy(
                    isLoading = false,
                    currenciesList = currenciesList.map { currencyModel ->
                        RadioButtonModel(
                            data = currencyModel,
                            isSelected = false,
                        )
                    }
                )
            }
        }
    }

    override fun changeCurrency(currency: CurrencyModel) {
        viewModelScope.launch {
            publishState {
                copy(
                    currenciesList = currenciesList.map { radioButtonModel ->
                        radioButtonModel.copy(
                            isSelected = radioButtonModel.data == currency
                        )
                    }
                )
            }

            publishEffect(
                CurrencyUiEffect.OnCurrencyChanged(
                    currencyName = currency.name,
                    currencyCode = currency.code
                )
            )
        }
    }
}
