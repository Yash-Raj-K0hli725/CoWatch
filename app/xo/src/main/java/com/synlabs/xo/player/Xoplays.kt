package com.synlabs.xo.player

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.ContentFrame


@OptIn(UnstableApi::class)
@Composable
fun XO(
    modifier: Modifier = Modifier,
    player: ExoPlayer,
    at: Long,
    duration: Long,
    isPlaying: Boolean,
    isBuffering: Boolean,
    isPlayerUIVisible: Boolean,
    onSeekBarPositionChange: (Long) -> Unit,
    onSeekBarPositionChangeFinished: (Long) -> Unit,
    onPlayPause: () -> Unit,
    onShowUI: () -> Unit
) {
    Box(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = null,
            onClick = onShowUI
        )
    ) {
        ContentFrame(
            player, modifier = Modifier.matchParentSize()
        )
        AnimatedVisibility(
            modifier = Modifier.matchParentSize(),
            visible = isPlayerUIVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            PlayerUI(
                modifier = Modifier,
                at = at,
                duration = duration,
                isPlaying = isPlaying,
                isBuffering = isBuffering,
                onSeekBarPositionChange = onSeekBarPositionChange,
                onSeekBarPositionChangeFinished = onSeekBarPositionChangeFinished,
                onPlayPause = onPlayPause
            )
        }
    }

}