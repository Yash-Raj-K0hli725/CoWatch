package com.cranoxz.streamroom.room.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.cranoxz.streamroom.room.viewmodel.RoomViewmodel


@Composable
fun WatchParty(
    roomId: String,
    viewModel: RoomViewmodel
) {
    DisposableEffect(roomId) {
        viewModel.findRoom(roomId)
        onDispose {

        }
    }

    AndroidView({ context ->
        PlayerView(context).apply {
//            player = viewModel.exoPlayer
            useController = true
        }
    }, modifier = Modifier.fillMaxSize())
}