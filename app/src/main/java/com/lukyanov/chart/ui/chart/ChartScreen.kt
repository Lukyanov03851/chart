package com.lukyanov.chart.ui.chart


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
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
import com.lukyanov.chart.core.ui.components.chart.DataItem
import com.lukyanov.chart.core.ui.components.chart.LinearChart
import com.lukyanov.chart.core.ui.components.chart.SelectableLinearChart
import com.lukyanov.chart.core.ui.components.spacer.VerticalSpacer
import com.lukyanov.chart.core.ui.components.tab.ChartTab
import com.lukyanov.chart.core.ui.components.topbar.TopBar
import com.lukyanov.chart.core.util.FormatCurrency
import com.lukyanov.chart.core.util.FormatDate
import com.lukyanov.chart.domain.model.DateRange
import com.lukyanov.chart.ui.chart.mvi.ChartUiIntent
import com.lukyanov.chart.ui.chart.mvi.ChartUiState
import com.lukyanov.chart.ui.chart.mvi.DefaultChartUiIntent
import com.lukyanov.chart.ui.chart.navigation.ChartNavAction
import com.lukyanov.chart.ui.chart.util.toTabTitle
import com.lukyanov.chart.ui.theme.ColorPrimaryDark
import com.lukyanov.chart.ui.theme.ColorPrimaryLight
import com.lukyanov.chart.ui.theme.Pink40
import com.lukyanov.chart.ui.theme.White

const val CHART_INDICATOR_COUNT = 11

@Preview
@Composable
fun ChartScreenPreview() {
    ChartScreen(
        state = ChartUiState(),
        intent = DefaultChartUiIntent,
        navAction = ChartNavAction(),
        paddingValues = PaddingValues.Absolute()
    )
}

@Composable
fun ChartScreen(
    state: ChartUiState,
    intent: ChartUiIntent,
    navAction: ChartNavAction,
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
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                TopBar(
                    modifier = Modifier.statusBarsPadding(),
                    header = stringResource(
                        id = R.string.chart_title,
                        state.currencyCode,
                        state.currencyName
                    ),
                    onStartIconClick = navAction.onNavigateBack
                )

                VerticalSpacer(space = 12.dp)

                PriceCard(state)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {

                    ChartBlock(state = state, intent = intent)

                    DateRangeTabs(
                        selectedTabIndex = state.selectedTab,
                        dateRanges = state.tabs,
                        onTabSelected = intent::selectTab
                    )

                    VisualChart(state)
                }
            }
        }
    }
}

@Composable
private fun PriceCard(state: ChartUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .background(
                color = ColorPrimaryLight.copy(
                    alpha = 0.4f
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            val amountText = FormatCurrency.formatFiatCurrency(
                value = if (state.isChartSelectionActive) {
                    state.selectedPrice
                } else {
                    state.currentPrice
                },
                currency = state.currencyCode
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                text = amountText,
                color = White,
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.W700
                )
            )
        }

        Text(
            modifier = Modifier.wrapContentSize(),
            text = FormatDate.formatDate(
                date = state.date,
                dateFormat = if (state.isChartSelectionActive) {
                    FormatDate.DAY_MONTH_YEAR_TIME_FORMAT
                } else {
                    FormatDate.DAY_MONTH_YEAR_FORMAT
                }
            ),
            color = Pink40,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )
        )
    }
}

@Composable
private fun ChartBlock(
    state: ChartUiState,
    intent: ChartUiIntent
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
    ) {
        if (state.historyData.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 22.dp,
                        end = 24.dp,
                        bottom = 22.dp
                    ),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                PriceItem(FormatCurrency.format(state.maxPrice))
                PriceItem(FormatCurrency.format(state.averagePrice))
                PriceItem(FormatCurrency.format(state.minPrice))
            }
        }

        SelectableLinearChart(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 14.dp,
                    horizontal = 16.dp
                ),
            data = state.historyData.map { model ->
                DataItem(
                    value = model.price,
                    data = model
                )
            },
            graphColor = White,
            pointerImage = R.drawable.ic_chart_pointer,
            indicatorCount = CHART_INDICATOR_COUNT,
            indicatorWidth = 1.dp,
            labelColor = White,
            onValueSelected = { priceModel ->
                intent.onSelectData(
                    historyData = priceModel
                )
            }
        )
    }
}

@Composable
private fun PriceItem(price: String) {
    Text(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .background(
                color = White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            ),
        text = price,
        color = White,
        style = TextStyle(
            fontSize = 11.sp,
            fontWeight = FontWeight.W400
        )
    )
}

@Composable
private fun DateRangeTabs(
    selectedTabIndex: Int,
    dateRanges: List<DateRange>,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        dateRanges.forEachIndexed { index, dateRange ->
            DateRangeTab(
                selectedTabIndex = selectedTabIndex,
                tabIndex = index,
                title = dateRange.toTabTitle(),
                onTabSelected = onTabSelected
            )
        }
    }
}

@Composable
private fun RowScope.DateRangeTab(
    selectedTabIndex: Int,
    tabIndex: Int,
    title: Int,
    onTabSelected: (Int) -> Unit
) {
    Tab(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topStart = 6.dp,
                    topEnd = 6.dp
                )
            )
            .weight(1f),
        selected = selectedTabIndex == tabIndex,
        onClick = {
            onTabSelected(tabIndex)
        }
    ) {
        ChartTab(
            selectedTabIndex = selectedTabIndex,
            title = title,
            tabIndex = tabIndex,
            titleColor = White,
            selectedTitleColor = White,
            selectedIndicatorColor = White,
        )
    }
}

@Composable
private fun ColumnScope.VisualChart(state: ChartUiState){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.Bottom
    ) {
        LinearChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            data = state.visualHistoryData,
            graphColor = ColorPrimaryDark
        )
    }
}