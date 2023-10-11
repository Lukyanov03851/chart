package com.lukyanov.chart.ui.chart.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.lukyanov.chart.core.ui.components.Loader
import com.lukyanov.chart.ui.chart.ChartScreen
import com.lukyanov.chart.ui.chart.ChartViewModel


internal const val CHART_ROUTE = "chart"
private const val KEY_CURRENCY_NAME = "currency_name"
private const val KEY_CURRENCY_CODE = "currency_code"

@SuppressLint("InlinedApi")
internal fun NavGraphBuilder.chartScreen(
    navAction: ChartNavAction,
    paddingValues: PaddingValues
) {
    composable("$CHART_ROUTE/{$KEY_CURRENCY_NAME}/{$KEY_CURRENCY_CODE}") {
        val viewModel = hiltViewModel<ChartViewModel>()
        val state = viewModel.uiState.collectAsStateWithLifecycle()

        Loader(
            modifier = Modifier.size(52.dp),
            isShowing = state.value.isLoading,
            onBack = navAction.onNavigateBack
        )

        ChartScreen(
            state = state.value,
            intent = viewModel,
            navAction = navAction,
            paddingValues = paddingValues
        )
    }
}

fun NavController.navigateToChart(
    currencyName: String,
    currencyCode: String,
    navOptions: NavOptions? = null
) {
    navigate(route = "$CHART_ROUTE/$currencyName/$currencyCode", navOptions = navOptions)
}

internal class ChartArgs(val currencyName: String, val currencyCode: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        currencyName = savedStateHandle[KEY_CURRENCY_NAME] ?: "",
        currencyCode = savedStateHandle[KEY_CURRENCY_CODE] ?: ""
    )
}
