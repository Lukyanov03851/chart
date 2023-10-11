package com.lukyanov.chart.ui.chart

import androidx.lifecycle.SavedStateHandle
import com.lukyanov.chart.core.view_model.BaseViewModel
import com.lukyanov.chart.domain.model.DateRange
import com.lukyanov.chart.domain.model.HistoryData
import com.lukyanov.chart.domain.use_case.GetPriceHistoryUseCase
import com.lukyanov.chart.ui.chart.mvi.ChartUiEffect
import com.lukyanov.chart.ui.chart.mvi.ChartUiIntent
import com.lukyanov.chart.ui.chart.mvi.ChartUiState
import com.lukyanov.chart.ui.chart.navigation.ChartArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAssetPriceHistoryUseCase: GetPriceHistoryUseCase,
) : BaseViewModel<ChartUiState, ChartUiEffect>(ChartUiState()),
    ChartUiIntent {

    private val args = ChartArgs(savedStateHandle)

    init {
        initChartState()
    }

    private fun initChartState() {
        viewModelScope.launch {
            publishState {
                copy(
                    isLoading = true,
                    currencyName = args.currencyName,
                    currencyCode = args.currencyCode
                )
            }
            loadVisualPriceHistory()
            loadPriceHistory(dateRange = DateRange.DAY)
        }
    }

    override fun selectTab(tabIndex: Int) {
        viewModelScope.launch {
            publishState {
                copy(
                    isLoading = true,
                    selectedTab = tabIndex
                )
            }

            loadPriceHistory(
                dateRange = uiState.value.tabs[tabIndex]
            )
        }
    }

    private suspend fun loadPriceHistory(dateRange: DateRange) {
        val priceHistory =
            uiState.value.historyCache[dateRange] ?: getAssetPriceHistoryUseCase.invoke(
                currency = args.currencyCode,
                range = dateRange,
            )

        uiState.value.historyCache[dateRange] = priceHistory

        publishState {
            copy(
                isLoading = false,
                historyData = priceHistory.historicalData,
                minPrice = priceHistory.lowPrice,
                maxPrice = priceHistory.maxPrice,
                averagePrice = priceHistory.averagePrice,
                tabs = uiState.value.tabs,
                currentPrice = if (dateRange == DateRange.DAY) {
                    priceHistory.historicalData.lastOrNull()?.price ?: 0.0
                } else {
                    uiState.value.currentPrice
                }
            )
        }

        publishPriceData(
            historyData = priceHistory.historicalData.lastOrNull() ?: HistoryData()
        )
    }

    private suspend fun loadVisualPriceHistory() {
        val priceHistory = getAssetPriceHistoryUseCase.invoke(
            currency = args.currencyCode,
            range = DateRange.WEEK,
        )

        publishState { copy(visualHistoryData = priceHistory.historicalData.map { it.price }) }
    }

    override fun onSelectData(historyData: HistoryData?) {
        if (historyData == null) {
            val selectedTab = uiState.value.tabs[uiState.value.selectedTab]
            publishPriceData(
                uiState.value.historyCache[selectedTab]?.historicalData?.lastOrNull()
                    ?: HistoryData()
            )
        } else {
            publishPriceData(
                historyData = historyData,
                isChartSelection = true
            )
        }
    }

    private fun publishPriceData(
        historyData: HistoryData,
        isChartSelection: Boolean = false
    ) {
        publishState {
            copy(
                selectedPrice = historyData.price,
                date = historyData.date,
                isChartSelectionActive = isChartSelection
            )
        }
    }
}
