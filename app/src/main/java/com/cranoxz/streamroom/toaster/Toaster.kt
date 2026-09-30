package com.cranoxz.streamroom.toaster

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cranoxz.streamroom.R
import com.cranoxz.streamroom.toaster.model.Alert
import com.cranoxz.streamroom.toaster.model.Succezz
import com.cranoxz.streamroom.toaster.model.Toastage
import com.cranoxz.streamroom.toaster.model.Warning
import com.cranoxz.streamroom.toaster.model.toast
import com.cranoxz.streamroom.util.Crimson
import com.cranoxz.streamroom.util.ForestGreen
import com.cranoxz.streamroom.util.Orange
import com.cranoxz.streamroom.util.SBlack
import kotlinx.coroutines.delay

private val smoothEase = CubicBezierEasing(
    0.18f,
    1.25f,
    0.4f,
    1.0f
)

@Composable
fun Toaster(modifier: Modifier = Modifier, toastage: Toastage) {
    var foreshadow by remember { mutableStateOf<Toastage?>(null) }

    if (toastage !is Toastage.Blank)
        foreshadow = toastage

    LaunchedEffect(toastage) {
        if (toastage !is Toastage.Blank) {
            delay(5000L)
            toast(Toastage.Blank)
        }
    }

    val display = if (toastage !is Toastage.Blank) toastage else foreshadow
    val (icon, color, desc) = when (display) {
        is Warning -> Triple(R.drawable.alert_circle, Color.Orange, "warning")
        is Succezz -> Triple(R.drawable.check_circle, Color.ForestGreen, "success")
        is Alert -> Triple(R.drawable.x_circle, Color.Crimson, "error")
        else -> return
    }

    AnimatedVisibility(
        visible = toastage !is Toastage.Blank && display != null,
        modifier = modifier,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(durationMillis = 550, easing = smoothEase)
        ) + scaleIn(
            initialScale = 0.7f,
            transformOrigin = TransformOrigin(0.5f, 0f), // Origin at top center
            animationSpec = tween(550, easing = smoothEase)
        ) + fadeIn(animationSpec = tween(550)),

        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(250, easing = smoothEase)
        ) + scaleOut(
            targetScale = 0.8f,
            transformOrigin = TransformOrigin(0.5f, 0f),
            animationSpec = tween(250, easing = smoothEase)
        ) + fadeOut(animationSpec = tween(150))

    ) {
        Row(
            modifier = Modifier
                .background(color = Color.SBlack, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                modifier = Modifier.size(24.dp),
                tint = color,
                contentDescription = desc
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = display?.bread ?: "",
                color = Color.White,
                maxLines = 2,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontFamily = FontFamily(Font(R.font.lato)),
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}