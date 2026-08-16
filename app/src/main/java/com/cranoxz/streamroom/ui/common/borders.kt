package com.cranoxz.streamroom.ui.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp = 2.dp,
    cornerRadius: Dp = 24.dp,
    dashLength: Dp = 8.dp,
    gapLength: Dp = 4.dp
): Modifier = this.drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    val halfStroke = strokeWidthPx / 2f
    val cornerRadiusPx = cornerRadius.toPx()

    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(dashLength.toPx(), gapLength.toPx()),
        phase = 0f
    )

    drawRoundRect(
        color = color,
        topLeft = androidx.compose.ui.geometry.Offset(halfStroke, halfStroke),
        size = androidx.compose.ui.geometry.Size(
            width = size.width - strokeWidthPx,
            height = size.height - strokeWidthPx
        ),
        cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
        style = Stroke(
            width = strokeWidthPx,
            pathEffect = pathEffect
        )
    )
}