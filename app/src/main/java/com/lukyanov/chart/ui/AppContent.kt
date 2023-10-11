package com.lukyanov.chart.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.lukyanov.chart.ui.chart.navigation.ChartNavAction
import com.lukyanov.chart.ui.chart.navigation.chartScreen
import com.lukyanov.chart.ui.chart.navigation.navigateToChart
import com.lukyanov.chart.ui.currency.navigation.CURRENCY_ROUTE
import com.lukyanov.chart.ui.currency.navigation.CurrencyNavAction
import com.lukyanov.chart.ui.currency.navigation.currencyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPaddingModifier ->
        NavHost(
            navController = navController,
            startDestination = CURRENCY_ROUTE,
            modifier = modifier.fillMaxSize()
        ) {
            currencyScreen(
                paddingValues = innerPaddingModifier,
                navAction = CurrencyNavAction(
                    onNavigateBack = {
                        (navController.context as Activity).finish()
                    },
                    onNavigateToChart = { currencyName, currencyCode ->
                        navController.navigateToChart(
                            currencyName = currencyName,
                            currencyCode = currencyCode
                        )
                    }
                )
            )

            chartScreen(
                paddingValues = innerPaddingModifier,
                navAction = ChartNavAction(
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            )
        }
    }
}
