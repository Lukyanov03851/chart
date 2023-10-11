package com.lukyanov.chart.core.ui.components.tab

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukyanov.chart.ui.theme.White

@Composable
fun ChartTab(
    selectedTabIndex: Int,
    @StringRes title: Int,
    tabIndex: Int,
    titleColor: Color = White,
    selectedTitleColor: Color = White,
    selectedIndicatorColor: Color = White,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxHeight(),
    ) {
        val (text, indicator) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(text) {
                    centerTo(parent)
                }
                .padding(horizontal = 16.dp),
            text = stringResource(id = title),
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            ),
            color = if (selectedTabIndex == tabIndex) {
                selectedTitleColor
            } else {
                titleColor
            },
            textAlign = TextAlign.Center
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(indicator) {
                bottom.linkTo(parent.bottom)
            }
            .height(1.dp)
            .background(
                color = if (selectedTabIndex == tabIndex) {
                    selectedIndicatorColor
                } else {
                    selectedIndicatorColor.copy(
                        alpha = 0.1f
                    )
                }
            )
        )
    }
}
