package com.lukyanov.chart.ui.chart.mvi

import com.lukyanov.chart.domain.model.HistoryData

interface ChartUiIntent {
    fun selectTab(tabIndex: Int)
    fun onSelectData(historyData: HistoryData?)
}

object DefaultChartUiIntent: ChartUiIntent{
    override fun selectTab(tabIndex: Int) {}
    override fun onSelectData(historyData: HistoryData?) {}
}
