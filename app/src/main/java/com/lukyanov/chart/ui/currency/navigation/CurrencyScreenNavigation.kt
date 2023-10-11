package com.lukyanov.chart.ui.currency.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukyanov.chart.core.ui.components.Loader
import com.lukyanov.chart.ui.currency.CurrencyScreen
import com.lukyanov.chart.ui.currency.CurrencyViewModel
import com.lukyanov.chart.ui.currency.mvi.CurrencyUiEffect

internal const val CURRENCY_ROUTE = "currency"

@SuppressLint("InlinedApi")
internal fun NavGraphBuilder.currencyScreen(
    navAction: CurrencyNavAction,
    paddingValues: PaddingValues,
) {
    composable(CURRENCY_ROUTE) {
        val viewModel = hiltViewModel<CurrencyViewModel>()
        val state = viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.uiEffect.collect { event ->
                when (event) {
                    is CurrencyUiEffect.OnCurrencyChanged -> {
                        navAction.onNavigateToChart(event.currencyName, event.currencyCode)
                    }
                }
            }
        }

        Loader(
            modifier = Modifier.size(52.dp),
            isShowing = state.value.isLoading,
            onBack = navAction.onNavigateBack
        )

        CurrencyScreen(
            state = state.value,
            intent = viewModel,
            navAction = navAction,
            paddingValues = paddingValues
        )
    }
}
