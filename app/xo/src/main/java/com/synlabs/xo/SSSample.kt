package com.synlabs.xo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.RetainedEffect
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.synlabs.xo.player.XO
import com.synlabs.xo.ui.theme.CoWatchTheme
import kotlinx.coroutines.delay

class SSSample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoWatchTheme {
                val context = LocalContext.current
                val owner = LocalLifecycleOwner.current
                var at by retain { mutableLongStateOf(0L) }
                var duration by retain { mutableLongStateOf(0L) }
                var isSeeking by retain { mutableStateOf(false) }
                var isBuffering by retain { mutableStateOf(false) }
                var isPlaying by retain { mutableStateOf(false) }
                var isPlayerUIVisible by retain { mutableStateOf(false) }

                val player = retain {
                    ExoPlayer.Builder(context.applicationContext).build().apply {
                        val item =
                            MediaItem.fromUri("https://cdn.cms.cowatch.in/SAMPLE/Suspend Functions vs. Flows In Kotlin _ Android_1080p60.mp4")
                        setMediaItem(item)
                        prepare()
                        play()
                    }
                }

                LaunchedEffect(isPlayerUIVisible, isSeeking, isPlaying) {
                    delay(5_000)
                    if (!isSeeking)
                        isPlayerUIVisible = false
                }

                LaunchedEffect(player, isPlaying, isSeeking) {
                    while (isPlaying) {
                        if (!isSeeking) {
                            at = player.currentPosition.coerceAtLeast(0)
                        }
                        delay(16L)
                    }
                }

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
                        onSeekBarPositionChange = { new -> isSeeking = true; at = new },
                        onSeekBarPositionChangeFinished = { player.seekTo(it) },
                        onPlayPause = {
                            when {
                                !isPlaying && player.playbackState == Player.STATE_ENDED -> {
                                    player.seekTo(0L)
                                    player.play()
                                }

                                !isPlaying -> player.play()

                                isPlaying -> player.pause()
                            }
                        },
                        onShowUI = { isPlayerUIVisible = !isPlayerUIVisible })
                }


                RetainedEffect(player) {
                    val listener = object : Player.Listener {
                        override fun onIsPlayingChanged(bool: Boolean) {
                            super.onIsPlayingChanged(bool)
                            isPlaying = bool
                        }

                        override fun onPlaybackStateChanged(playbackState: Int) {
                            super.onPlaybackStateChanged(playbackState)
                            isBuffering = playbackState == Player.STATE_BUFFERING
                            if (duration == 0L && playbackState == Player.STATE_READY) {
                                duration = player.duration.coerceAtLeast(0)
                            }
                        }
                    }


                    val playObserver = (LifecycleEventObserver { _, event ->
                        when (event) {
                            Lifecycle.Event.ON_STOP if (isPlaying) -> player.pause()
                            Lifecycle.Event.ON_RESUME if (player.playbackState == Player.STATE_READY && !isPlaying) -> player.play()
                            else -> Unit
                        }
                    })

                    player.addListener(listener)
                    owner.lifecycle.addObserver(playObserver)

                    onRetire {
                        lifecycle.removeObserver(playObserver)
                        player.removeListener(listener)
                        player.release()
                    }
                }
            }
        }
    }
}