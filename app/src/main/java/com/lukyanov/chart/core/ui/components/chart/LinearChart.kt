package com.lukyanov.chart.core.ui.components.chart

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun LinearChart(
    modifier: Modifier = Modifier,
    data: List<Double>,
    graphColor: Color,
) {
    val fillGraphColor = graphColor.copy(alpha = 0.2f)

    val minValue = data.minByOrNull { it }
    val maxValue = data.maxByOrNull { it }

    val (lowerValue, upperValue) = remember(key1 = data) {
        calculateChartBounds(minValue ?: 0.0, maxValue ?: 0.0)
    }

    Canvas(modifier = modifier) {

        val graphHeight = size.height

        if (data.isNotEmpty()) {
            val spacePerPoint = size.width / data.size
            var lastX: Float

            val strokePath = Path().also { path ->
                val height = size.height

                for (i in data.indices) {
                    val info = data[i]
                    val nextInfo = data.getOrNull(i + 1) ?: data.last()

                    val leftRatio = (info - lowerValue) / (upperValue - lowerValue)
                    val rightRatio = (nextInfo - lowerValue) / (upperValue - lowerValue)

                    val x1 = i * spacePerPoint
                    val y1 = height - (leftRatio * height).toFloat()

                    val x2 = (i + 1) * spacePerPoint
                    val y2 = height - (rightRatio * height).toFloat()

                    lastX = (x1 + x2) / 2
                    val lastY = (y1 + y2) / 2

                    if (i == 0) {
                        path.moveTo(x1, y1)
                    } else {
                        path.quadraticBezierTo(x1, y1, lastX, lastY)
                    }

                    if (i == data.lastIndex) {
                        path.lineTo(x2, y2)
                    }
                }
            }

            drawGraphic(
                strokePath = strokePath,
                graphHeight = graphHeight,
                fillGraphColor = fillGraphColor,
            )
        }
    }
}

private fun DrawScope.drawGraphic(
    strokePath: Path,
    graphHeight: Float,
    fillGraphColor: Color
) {

    val fillPath = android.graphics.Path(strokePath.asAndroidPath())
        .asComposePath()
        .apply {
            lineTo(size.width, graphHeight)
            lineTo(0f, graphHeight)
            close()
        }

    drawPath(
        path = fillPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                fillGraphColor,
                Color.Transparent
            ),
            endY = graphHeight
        ),
    )
}