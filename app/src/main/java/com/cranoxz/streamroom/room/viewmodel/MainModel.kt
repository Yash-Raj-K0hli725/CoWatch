package com.cranoxz.streamroom.room.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.cranoxz.streamroom.core.network.Konnect
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val _at = MutableStateFlow(0L)
    val at = _at.asStateFlow()
    private val _duration = MutableStateFlow(0L)
    val duration = _duration.asStateFlow()
    private var isSeeking: Boolean = false
    val isBuffering = MutableStateFlow(false)
    val isPlaying = MutableStateFlow(false)
    val isPlayerUIVisible = MutableStateFlow(false)

    var ticker: Job? = null

    val playstener: Player.Listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            isBuffering.value = playbackState == Player.STATE_BUFFERING
            if (_duration.value == 0L && playbackState == Player.STATE_READY) {
                _duration.value = player.duration.coerceAtLeast(0L)
            }
        }

        override fun onIsPlayingChanged(bool: Boolean) {
            super.onIsPlayingChanged(bool)
            isPlaying.value = bool
            if (bool) startTicker() else stopTicker()
        }
    }

    fun showControls() {
        isPlayerUIVisible.value = !isPlayerUIVisible.value
    }

    fun onPlayPause() {
        when {
            !isPlaying.value && player.playbackState == Player.STATE_ENDED -> {
                player.seekTo(0L); player.play()
            }

            !isPlaying.value -> player.play()
            isPlaying.value -> player.pause()
            else -> Unit
        }
    }

    fun seekTo(new: Long) {
        player.seekTo(new)
        isSeeking = false
    }

    fun whileSeek(new: Long) {
        isSeeking = true
        _at.value = new
    }

    val player = ExoPlayer.Builder(application.applicationContext).build().apply {
        addListener(playstener)
        val item =
            MediaItem.fromUri("https://cdn.cms.cowatch.in/SAMPLE/Suspend Functions vs. Flows In Kotlin _ Android_1080p60.mp4")
        setMediaItem(item)
        prepare()
    }

    fun startTicker() {
        if (ticker?.isActive == true) return
        ticker = viewModelScope.launch {
            while (isActive) {
                if (isPlaying.value && !isSeeking) {
                    _at.value = player.currentPosition.coerceAtLeast(0L)
                }
                delay(16L)
            }
        }
    }

    fun stopTicker() {
        ticker?.cancel()
        ticker = null
    }

    fun findRoom(roomId: String) {
        viewModelScope.launch {
            val response = Konnect<String> {
                method = HttpMethod.Get
                url("api/rooms/find")
                parameter("room_id", roomId)
            }
            Log.e("yash", "response is $response")
        }
    }

    override fun onCleared() {
        player.removeListener(playstener)
        player.release()
    }
}