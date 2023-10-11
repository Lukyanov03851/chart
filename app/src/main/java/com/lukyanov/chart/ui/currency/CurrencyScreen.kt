package com.lukyanov.chart.ui.currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukyanov.chart.R
import com.lukyanov.chart.core.ui.components.Gradients
import com.lukyanov.chart.core.ui.components.radio.RadioButton
import com.lukyanov.chart.core.ui.components.spacer.VerticalSpacer
import com.lukyanov.chart.core.ui.components.topbar.TopBar
import com.lukyanov.chart.ui.currency.mvi.CurrencyUiIntent
import com.lukyanov.chart.ui.currency.mvi.CurrencyUiState
import com.lukyanov.chart.ui.currency.mvi.DefaultCurrencyUiIntent
import com.lukyanov.chart.ui.currency.navigation.CurrencyNavAction
import com.lukyanov.chart.ui.theme.Black
import com.lukyanov.chart.ui.theme.White

@Preview
@Composable
fun CurrencyScreenPreview() {
    CurrencyScreen(
        state = CurrencyUiState(),
        intent = DefaultCurrencyUiIntent(),
        navAction = CurrencyNavAction(),
        paddingValues = PaddingValues(0.dp),
    )
}

@Composable
fun CurrencyScreen(
    state: CurrencyUiState,
    intent: CurrencyUiIntent,
    navAction: CurrencyNavAction,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Gradients.getBackground()
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                modifier = Modifier.statusBarsPadding(),
                header = stringResource(id = R.string.choose_currency),
                onStartIconClick = {
                    navAction.onNavigateBack()
                }
            )

            VerticalSpacer(space = 12.dp)

            val contentShape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = White,
                        shape = contentShape
                    )
                    .clip(contentShape)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 10.dp
                    )
                ) {
                    items(state.currenciesList) { item ->
                        RadioButton(
                            modifier = Modifier.height(42.dp),
                            radioButtonModel = item,
                            onOptionSelected = {
                                intent.changeCurrency(item.data)
                            }
                        ) {
                            Text(
                                modifier = Modifier.wrapContentSize(),
                                text = "${item.data.code} - ${item.data.name}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500
                                ),
                                color = Black,
                            )
                        }
                    }
                }
            }
        }
    }
}
