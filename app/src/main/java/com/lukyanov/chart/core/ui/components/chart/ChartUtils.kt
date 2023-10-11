package com.lukyanov.chart.core.ui.components.chart

fun <T> List<DataPoint<T>>.getCorrespondingData(x: Float): DataPoint<T>? {
    val previousIndex = indexOfLast { point -> point.x <= x }
    val nextIndex = indexOfFirst { point -> point.x >= x }

    return if (nextIndex == -1) {
        if (previousIndex == -1) {
            null
        } else {
            get(previousIndex)
        }
    } else {
        if (previousIndex == -1) {
            get(nextIndex)
        } else {
            val previousPoint = get(previousIndex)
            val nextPoint = get(nextIndex)

            val nearestPoint = if ((x - previousPoint.x) < (nextPoint.x - x)) {
                previousPoint
            } else {
                nextPoint
            }

            DataPoint(x = x, data = nearestPoint.data)
        }
    }
}

fun calculateChartBounds(minValue: Double, maxValue: Double): Pair<Double, Double> {
    val bounds = (minValue * 100) / maxValue

    return when {
        bounds > 98 -> Pair(minValue * 0.998, maxValue / 0.998)
        bounds > 90 -> Pair(minValue * 0.99, maxValue / 0.99)
        bounds > 60 -> Pair(minValue * 0.97, maxValue / 0.97)
        bounds > 30 -> Pair(minValue * 0.93, maxValue / 0.93)
        else -> Pair(minValue * 0.90, maxValue / 0.90)
    }
}
