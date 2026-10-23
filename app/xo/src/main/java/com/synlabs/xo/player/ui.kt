package com.synlabs.xo.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synlabs.xo.R
import java.util.Locale

@Composable
fun PlayerUI(
    modifier: Modifier = Modifier,
    at: Long,
    duration: Long,
    isBuffering: Boolean,
    isPlaying: Boolean,
    onSeekBarPositionChange: (Long) -> Unit,
    onSeekBarPositionChangeFinished: (Long) -> Unit,
    onPlayPause: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, Color.Black
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            IconButton(onClick = onPlayPause, modifier = Modifier.size(48.dp)) {
                Icon(
                    painter = painterResource(if (isPlaying) R.drawable.pause else R.drawable.play),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(at.formatToDuration(), color = Color.White, fontSize = 12.sp)
            Slider(
                value = at.toFloat(),
                onValueChange = { new -> onSeekBarPositionChange(new.toLong()) },
                onValueChangeFinished = { onSeekBarPositionChangeFinished(at) },
                valueRange = 0f..duration.toFloat(),
                modifier = Modifier.weight(1f),
            )
            Text(duration.formatToDuration(), color = Color.White, fontSize = 12.sp)
        }
    }

}

private fun Long.formatToDuration(): String {
    val toSeconds = this / 1000
    val hours = toSeconds / 3600
    val minutes = (toSeconds % 3600) / 60
    val seconds = toSeconds % 60
    return if (hours > 0) String.format(
        Locale.ENGLISH,
        "%d:%02d:%02d",
        hours,
        minutes,
        seconds
    ) else String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds)
}