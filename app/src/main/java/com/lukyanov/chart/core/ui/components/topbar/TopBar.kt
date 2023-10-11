package com.lukyanov.chart.core.ui.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukyanov.chart.R
import com.lukyanov.chart.core.ui.components.spacer.HorizontalSpacer
import com.lukyanov.chart.ui.theme.White

@Preview
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    header: String = "",
    headerColor: Color = White,
    onStartIconClick: () -> Unit = {},
    onEndIconClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Start Icon",
            modifier = Modifier
                .size(size = 22.dp)
                .clip(shape = CircleShape)
                .clickable(
                    onClick = onStartIconClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ),
        )

        HorizontalSpacer(space = 16.dp)

        Text(
            text = header,
            modifier = Modifier.wrapContentSize(),
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.W600
            ),
            color = headerColor,
        )
    }
}
