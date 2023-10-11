package com.lukyanov.chart.ui.currency.navigation

class CurrencyNavAction(
    val onNavigateBack: () -> Unit = {},
    val onNavigateToChart: (currencyName: String, currencyCode: String) -> Unit = { _, _ -> },
)
