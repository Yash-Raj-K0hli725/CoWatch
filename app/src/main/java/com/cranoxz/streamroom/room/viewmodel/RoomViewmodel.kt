package com.cranoxz.streamroom.room.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.cranoxz.streamroom.core.network.Konnect
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.coroutines.launch

class RoomViewmodel(application: Application) : AndroidViewModel(application) {

    val exoPlayer = ExoPlayer.Builder(application).build().apply {
        playWhenReady = true // Automatically begin playing when chunks are downloaded
    }

//    private val socket = HttpClient {
//        install(WebSockets)
//    }

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
        super.onCleared()
        exoPlayer.release() // Always clean up player threads from memory
//        socket.close()
    }

}