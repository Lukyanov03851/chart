package com.lukyanov.chart.core.ui.components.chart

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val BOUND_LINES_ALPHA_MULTIPLIER = 4
const val SPACING_Y = 30f

@Composable
fun <T> SelectableLinearChart(
    modifier: Modifier = Modifier,
    data: List<DataItem<T>>,
    graphColor: Color,
    labelColor: Color,
    lineAlpha: Float = 0.05f,
    indicatorCount: Int = 0,
    indicatorWidth: Dp,
    @DrawableRes pointerImage: Int,
    onValueSelected: (T?) -> Unit,
) {
    val fillGraphColor = graphColor.copy(alpha = 0.4f)
    val lineWidth = 1.dp

    val painter = rememberVectorPainter(image = ImageVector.vectorResource(id = pointerImage))
    val offsetX = remember { mutableFloatStateOf(value = 0f) }
    val offsetDraggingX = remember { mutableFloatStateOf(value = 0f) }
    val isDragActive = remember { mutableStateOf(value = false) }
    val dotsList = remember { mutableStateOf(listOf<Offset>()) }
    val dataList = remember { mutableStateOf(listOf<DataItem<T>>()) }

    val minValue = data.minByOrNull { it.value }
    val maxValue = data.maxByOrNull { it.value }

    val (lowerValue, upperValue) = remember(key1 = data) {
        calculateChartBounds(minValue?.value ?: 0.0, maxValue?.value ?: 0.0)
    }

    Canvas(modifier = modifier
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { offset ->
                    offsetX.floatValue = offset.x
                    isDragActive.value = true
                },
                onTap = {
                    isDragActive.value = false
                    offsetDraggingX.floatValue = 0f
                    onValueSelected(null)
                }
            )
        }
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = { offset ->
                    offsetX.floatValue = offset.x
                    isDragActive.value = true
                },
                onDragEnd = {
                    isDragActive.value = false
                    offsetDraggingX.floatValue = 0f
                    onValueSelected(null)
                }
            ) { _, dragAmount ->
                offsetDraggingX.floatValue = offsetDraggingX.floatValue + dragAmount.x
            }
        }
    ) {
        val stepY = (size.height) / (indicatorCount - 1)
        val graphHeight = size.height

        drawLines(
            count = indicatorCount,
            spacingY = 0f,
            stepY = stepY,
            lineColor = labelColor,
            lineAlpha = lineAlpha,
            lineWidth = indicatorWidth
        )

        if (data.isNotEmpty()) {
            val spacePerPoint = size.width / data.size
            var lastX: Float
            val dataPointsList = mutableListOf<DataPoint<T>>()

            val strokePath = Path().also { path ->

                for (i in data.indices) {
                    val info = data[i]
                    val nextInfo = data.getOrNull(i + 1) ?: data.last()

                    val leftRatio = (info.value - lowerValue) / (upperValue - lowerValue)
                    val rightRatio = (nextInfo.value - lowerValue) / (upperValue - lowerValue)

                    val x1 = i * spacePerPoint
                    val y1 = size.height - SPACING_Y - (leftRatio * size.height).toFloat()

                    val x2 = (i + 1) * spacePerPoint
                    val y2 = size.height - SPACING_Y - (rightRatio * size.height).toFloat()

                    lastX = (x1 + x2) / 2
                    val lastY = (y1 + y2) / 2

                    if (i == 0) {
                        dataPointsList.add(DataPoint(x = x1, data = info.data))
                        path.moveTo(x1, y1)
                    } else {
                        dataPointsList.add(DataPoint(x = x1, data = info.data))
                        path.quadraticBezierTo(x1, y1, lastX, lastY)
                    }

                    if (i == data.lastIndex) {
                        path.lineTo(x2, y2)
                    }
                }
            }

            if (dataList.value != data) {
                dataList.value = data
                val pathMeasure = PathMeasure()
                pathMeasure.setPath(strokePath, false)

                val dots = mutableListOf<Offset>()
                val pathLengthSegment = pathMeasure.length / size.width.toInt()
                for (i in 0..size.width.toInt()) {
                    dots.add(pathMeasure.getPosition(i * pathLengthSegment))
                }
                dotsList.value = dots
            }

            drawGraphic(
                strokePath = strokePath,
                graphHeight = graphHeight,
                graphColor = graphColor,
                graphLineWidth = lineWidth,
                fillGraphColor = fillGraphColor,
            )

            if (isDragActive.value) {
                drawDraggableElement(
                    painter = painter,
                    dotsList = dotsList.value,
                    offsetX = offsetX.floatValue + offsetDraggingX.floatValue,
                    lineColor = labelColor,
                    lineAlpha = 1f,
                    lineWidth = lineWidth,
                    dataPointsList = dataPointsList,
                    onValueSelected = onValueSelected,
                )
            }
        }
    }
}

private fun DrawScope.drawGraphic(
    strokePath: Path,
    graphHeight: Float,
    graphColor: Color,
    graphLineWidth: Dp,
    fillGraphColor: Color
) {

    val fillPath = android.graphics.Path(strokePath.asAndroidPath())
        .asComposePath()
        .apply {
            lineTo(size.width, graphHeight - SPACING_Y)
            lineTo(0f, graphHeight + SPACING_Y)
            close()
        }

    drawPath(
        path = fillPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                fillGraphColor,
                Color.Transparent
            ),
            endY = graphHeight - SPACING_Y
        ),
    )

    drawPath(
        path = strokePath,
        color = graphColor,
        style = Stroke(
            width = graphLineWidth.toPx(),
            cap = StrokeCap.Round
        )
    )
}

private fun <T> DrawScope.drawDraggableElement(
    painter: VectorPainter,
    dotsList: List<Offset>,
    offsetX: Float,
    lineColor: Color,
    lineAlpha: Float,
    lineWidth: Dp,
    dataPointsList: List<DataPoint<T>>,
    onValueSelected: (T?) -> Unit,
) {
    var x = offsetX

    if (x < 0){
        x = 0f
    }

    if (x > size.width) {
        x = size.width
    }

    val lineX = x - (lineWidth.toPx() / 2)

    val dottedPath = Path().apply {
        moveTo(lineX, 0f)
        lineTo(lineX, size.height)
    }

    val dotWidth = painter.intrinsicSize.width

    val dotData = dataPointsList.getCorrespondingData(x = x)

    onValueSelected(dotData?.data)

    drawDividerLine(
        path = dottedPath,
        lineColor = lineColor,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        isDashedLine = true
    )

    val y = dotsList.firstOrNull { it.x >= x }?.y ?: 0f

    translate(x - (dotWidth / 2) - (lineWidth.toPx() / 2), y - (dotWidth / 2)) {
        with(painter) {
            draw(painter.intrinsicSize)
        }
    }
}

private fun DrawScope.drawDividerLine(
    path: Path,
    lineColor: Color,
    lineAlpha: Float,
    lineWidth: Dp,
    onSize: Float = 10f,
    offSize: Float = 20f,
    isDashedLine: Boolean
) {
    drawPath(
        path = path,
        color = lineColor.copy(alpha = lineAlpha),
        style = Stroke(
            width = lineWidth.toPx(),
            cap = StrokeCap.Round,
            pathEffect = if (isDashedLine) {
                PathEffect.dashPathEffect(floatArrayOf(onSize, offSize), 0f)
            } else {
                null
            }
        )
    )
}

private fun DrawScope.drawLines(
    count: Int,
    spacingY: Float,
    stepY: Float,
    lineColor: Color,
    lineAlpha: Float,
    lineWidth: Dp,
) {
    var lineY = 0f

    for (i in 0 until count) {
        if (i > 0) {
            lineY += stepY
        }

        val path = Path().apply {
            moveTo(0f, lineY + spacingY)
            lineTo(size.width - lineWidth.toPx(), lineY + spacingY)
        }

        drawDividerLine(
            path = path,
            lineColor = lineColor,
            lineAlpha = if (i == count - 1) {
                lineAlpha * BOUND_LINES_ALPHA_MULTIPLIER
            } else {
                lineAlpha
            },
            lineWidth = lineWidth,
            isDashedLine = false
        )
    }

    val verticalPath = Path().apply {
        moveTo(size.width, 0f)
        lineTo(size.width, size.height)
    }

    drawDividerLine(
        path = verticalPath,
        lineColor = lineColor,
        lineAlpha = lineAlpha * BOUND_LINES_ALPHA_MULTIPLIER,
        lineWidth = lineWidth,
        isDashedLine = false
    )
}
