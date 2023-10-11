package com.lukyanov.chart.core.ui.components.radio

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.lukyanov.chart.R
import com.lukyanov.chart.core.ui.components.spacer.HorizontalSpacer

@Composable
fun <Data> RadioButton(
    modifier: Modifier = Modifier,
    radioButtonModel: RadioButtonModel<Data>,
    onOptionSelected: () -> Unit,
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
            .toggleable(
                value = radioButtonModel.isSelected,
                role = Role.RadioButton,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onValueChange = {
                    onOptionSelected()
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = if (radioButtonModel.isSelected) {
                    R.drawable.ic_radio_on
                } else {
                    R.drawable.ic_radio_off
                }
            ),
            contentDescription = null,
        )

        HorizontalSpacer(space = 10.dp)

        content()
    }
}