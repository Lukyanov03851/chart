package com.lukyanov.chart.core.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import com.lukyanov.chart.ui.theme.ColorPrimaryDark
import com.lukyanov.chart.ui.theme.ColorPrimaryLight

object Gradients {

    fun getBackground(alpha: Float = 1F): Brush {
        return object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                return LinearGradientShader(
                    colors = listOf(
                        ColorPrimaryDark.copy(alpha = alpha),
                        ColorPrimaryLight.copy(alpha = alpha)
                    ),
                    from = Offset(0f, 0f), // top left corner
                    to = Offset(size.width, size.height), // bottom right corner
                )
            }
        }
    }
}
