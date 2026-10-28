package com.synlabs.xo.watch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.synlabs.xo.player.XO

@Composable
fun CoWatch(
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
    val scope = LocalLifecycleOwner.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        XO(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .statusBarsPadding()
                .background(color = Color.Black)
                .padding(horizontal = 16.dp),
            player = player,
            at = at,
            duration = duration,
            isPlaying = isPlaying,
            isBuffering = isBuffering,
            isPlayerUIVisible = isPlayerUIVisible,
            onSeekBarPositionChange = onSeekBarPositionChange,
            onSeekBarPositionChangeFinished = onSeekBarPositionChangeFinished,
            onPlayPause = onPlayPause,
            onShowUI = onShowUI
        )
    }
    DisposableEffect(scope) {
        val listener = (LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> player.pause()
                Lifecycle.Event.ON_RESUME if (player.playbackState == Player.STATE_READY && !isPlaying) -> player.play()
                else -> Unit
            }
        })
        scope.lifecycle.addObserver(listener)
        onDispose {
            scope.lifecycle.removeObserver(listener)
        }
    }

}