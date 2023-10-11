package com.lukyanov.chart.core.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.lukyanov.chart.ui.theme.White

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    isShowing: Boolean = true,
    onBack: () -> Unit = {}
) {
    if (isShowing) {
        Dialog(
            onDismissRequest = {
                // no-op
            }
        ) {
            BackHandler(onBack = onBack)

            CircularProgressIndicator(
                modifier = modifier,
                color = White,
            )
        }
    }
}
