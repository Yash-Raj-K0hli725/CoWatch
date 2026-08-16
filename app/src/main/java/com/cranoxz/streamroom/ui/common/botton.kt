package com.cranoxz.streamroom.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun Botton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    background: Color,
    strokeColor: Color = Color.Transparent,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
    onClick: () -> Unit,
    text: @Composable () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = background),
        contentPadding = padding,
        border = BorderStroke(width = 1.dp, color = strokeColor)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(
                    24.dp
                ), color = Color.White
            )
            Spacer(Modifier.width(8.dp))
        } else if (icon != null) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(Modifier.width(8.dp))
        }
        text()
    }
}